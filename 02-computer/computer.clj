(require '[clojure.string :refer [split]])

(defn evaluate-1 [instruction]
  ())

(defn evaluate [instructions]
  (let [opcode ])
  instructions)
  ;(cond ))

"1,0,0,0,99"

; + 1 1 = 2
(let [string-instructions (split (slurp (java.io.BufferedReader. *in*)) #",")
      instructions (map clojure.edn/read-string string-instructions)
      computer-state (evaluate instructions)]
  (println computer-state))
