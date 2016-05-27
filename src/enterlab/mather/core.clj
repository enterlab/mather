(ns enterlab.mather.core
  (:require [clojure.edn :as edn]
            [enterlab.mather.i18n :refer [txt]])
  (:import java.util.Date
           java.text.SimpleDateFormat))

(defn read-results [user]
  (let [results-str (try
                      (slurp (format "./%s-results.mather" user))
                      (catch Throwable t
                        "{}"))]
    (edn/read-string results-str)))

(defn stats [all-results a b]
  (let [results (all-results [a b])
        sorted (sort-by last results)]
    {:best (first sorted)
     :trials (count results)}))

(defn write-result [user results result-path result]
  (let [updated-results (update-in results [result-path] (fnil conj []) (into [(Date.)] result))]
    (spit (format "./%s-results.mather" user)
            (str updated-results)
            :append false)))

(defn question [a b lang]
  (print (format (txt lang :question-add-2) a b))
  (flush)
  (let [answer (read-line)
        number-answer (edn/read-string answer)
        correct-answer (* a b)]
    (if (= number-answer correct-answer)
      (do
        (println (txt lang :answer-correct))
        true)
      (do
        (println (format (txt lang :answer-wrong) correct-answer))
        false))))

(defn start-quiz [min max n correct lang]
      (if (= 0 n)
        (fn [start]
          (let [end (System/currentTimeMillis)
                real-seconds (/ (- end start) 1000)
                secs (Math/round (* 1.0 real-seconds))
                secs-correct (if (> correct 0)
                              (/ real-seconds correct)
                              0)]
            {:correct correct
             :seconds secs
             :secs-per-correct secs-correct}))
        (let [the-range (range min max)
              a (rand-nth the-range)
              b (rand-nth the-range)]
          (recur
            min max
            (dec n)
            (if (question a b lang)
              (inc correct)
              correct)
            lang))))

(defn quiz [user min-int max-int questions lang] ;; Refactor this
  (println (format (txt lang :quiz-headline)
                   min-int max-int questions))
  (let [results (read-results user)
        {:keys [best trials]} (stats results min-int max-int)
        _ (println (format (txt lang :quiz-tried-times-before)
                           trials))
        _ (when best
            (println (format (txt lang :quiz-current-best)
                             (* 1.0 (last best))
                             (.format
                              (SimpleDateFormat. (txt lang :date-format))
                              (first best)))))
        _ (println (txt lang :quiz-start-when-ready))
        _ (read-line)
        start-millis (System/currentTimeMillis)
        result ((start-quiz min-int max-int questions 0 lang) start-millis)
        {:keys [correct seconds secs-per-correct]} result]
    (write-result user results ;; Too many input. Use result as input, assoc'ed with additional data
                  [min-int max-int]
                  [questions correct seconds (if (> secs-per-correct 0)
                                              secs-per-correct
                                              999999)]) ;; Obviously wrong
    (println (format (txt lang :quiz-result)
                correct
                (- questions correct)
                seconds
                (if (> secs-per-correct 0)
                  (format "%.2f" (* 1.0 secs-per-correct))
                  (txt lang :quiz-result-infinity))))
    ;; When PB, print extra line!
    (when (and best (not= 0 secs-per-correct) (< secs-per-correct (last best)))
      (println (format (txt lang :quiz-personal-best)
                       (* 1.0 (- (last best) secs-per-correct)))))))
