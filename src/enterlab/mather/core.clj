(ns enterlab.mather.core
  (:require [clojure.edn :as edn])
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

(defn question [a b]
  (println (format "Hvad er %d gange %d?" a b))
  (flush)
  (let [answer (read-line)
        number-answer (edn/read-string answer)
        correct-answer (* a b)]
    (flush)
    (if (= number-answer correct-answer)
      (do
        (println "Korrekt!")
        true)
      (do
        (println (format "Forkert, det er %d" correct-answer))
        false))))

(defn start-quiz [min max n correct]
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
            (if (question a b)
              (inc correct)
              correct)))))

(defn quiz [user min-int max-int questions]
  (println (format "\n===== gang tal mellem %d og %d (%d repetitioner) =====" min-int max-int questions))
  (let [start-millis (System/currentTimeMillis)
        results (read-results user)
        {:keys [best trials]} (stats results min-int max-int)
        _ (println (format "  - Forsøgt %d gange før" trials))
        _ (when best
            (println (format "  - Din rekord på %.1f s/korrekt svar er fra %s\n=====" (* 1.0 (last best)) (.format (SimpleDateFormat. "yyyy-MM-dd") (first best)))))
        result ((start-quiz min-int max-int questions 0) start-millis)
        {:keys [correct seconds secs-per-correct]} result]
    (write-result user results [min-int max-int] [questions correct seconds (if (> secs-per-correct 0) secs-per-correct -1)])
    (println (format "RESULTAT:\n Du har %d korrekte og %d forkerte svar.\n Det tog dig %d sekunder at svare.\n Det er ca. %s sekunder pr. korrekt svar."
                correct
                (- questions correct)
                seconds
                (if (> secs-per-correct 0)
                  (format "%.1f" (* 1.0 secs-per-correct))
                  "UENDELIGT MANGE")))
    (when (and best (< secs-per-correct (last best)))
      (println (format " *** TILLYKKE, du har slået din rekord med %.1f sekunder pr. korrekt svar!! :-) ***" (* 1.0 (- (last best) secs-per-correct)))))))
