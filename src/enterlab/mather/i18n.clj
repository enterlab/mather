(ns enterlab.mather.i18n)

(defonce default-lang :en)

(defonce texts
  {:da {
        :quiz-headline "\n===== Gang tal mellem %d og %d (%d repetitioner) ====="
        :quiz-tried-times-before "  - Forsøgt %d gange før"
        :quiz-current-best "  - Din rekord på %.1f sekunder pr. korrekt svar er fra %s"
        :quiz-start-when-ready "===== Tryk på ENTER når du er klar!! ====="
        :quiz-result "===== RESULTAT =====\n Du har %d korrekte og %d forkerte svar.\n Det tog dig cirka %d sekunder at svare.\n Det er %s sekunder pr. korrekt svar."
        :quiz-result-infinity "UENDELIGT MANGE"
        :quiz-personal-best " *** TILLYKKE, du har slået din rekord med %.2f sekunder pr. korrekt svar!! :-) ***"
        :question-add-2 "%d gange %d? "
        :answer-correct "Korrekt!"
        :answer-wrong "Forkert, det er %d"
        :date-format "dd/MM yyyy"
       }
   :en {
        :quiz-headline "\n===== Multiply numbers between %d and %d (%d repetitions) ====="
        :quiz-tried-times-before "  - Tried %d times before"
        :quiz-current-best "  - Your record of %.1f seconds pr. correct answer is from %s"
        :quiz-start-when-ready "===== Press ENTER when ready!! ====="
        :quiz-result "===== RESULT =====\n You had %d correct and %d wrong answers.\n It took you approximately %d seconds to answer.\n That's %s seconds pr. correct answer."
        :quiz-result-infinity "INFINITY"
        :quiz-personal-best " *** CONGRATULATIONS, you've beat your own personal best with %.2f seconds pr. correct answer!! :-) ***"
        :question-add-2 "%d times %d? "
        :answer-correct "Correct!"
        :answer-wrong "Wrong, it's %d"
        :date-format "MM-dd-yyyy"
       }})

(defn txt [_lang & txt-path]
  (let [lang (or (_lang texts) (default-lang texts))]
    (get-in lang txt-path)))
