(require '[clojure.string :refer [split]])

(defn adjacent-pairs [l]
  (map vector (butlast l) (rest l)))

(defn adjacent-triplets [l]
  (map vector (butlast (butlast l)) (butlast (rest l)) (rest (rest l))))

;; (adjacent-triplets [1 2 3 4])

(defn has-adjacent-digits? [digits]
  (let [triplets (adjacent-triplets digits)]
    (some true? (map (fn [[a b c]]
                       (and c
                            (= a b)
                            (not= b c))) triplets))))

;; (first '())
;; (defn group-start [digit remainder]
;;   (let [f (first digits)]
;;     (if (nil? f)
;;       [digit]
;;       (if (= digit f)
;;         )))
;;   (if ))
;; (defn group-by-number [digits]
;;   (let [f (first digits)]
;;     (if (nil? f)
;;       []
;;       ()))
;;   (take-while ))

; good
(has-adjacent-digits? [1 1 3])

(defn has-adjacent-digits? [digits]
  (let [pairs (adjacent-pairs digits)
        same-pairs (map (fn [[a b]] (= a b)) pairs)
        same-pair-pairs (adjacent-pairs same-pairs)
        same-pairs-disqualified (map (fn [[a b]] (and a b)) same-pair-pairs)
        left-aligned-disqualified (concat '(false) same-pairs-disqualified)
        right-aligned-disqualified (concat same-pairs-disqualified '(false))
        triplets (map vector same-pairs left-aligned-disqualified right-aligned-disqualified)
        ;; interleaved (interleave same-pairs same-pairs-disqualified)
        ;; value-and-disqualified (map vector same-pairs lined-up-disqualified)
        ;; qualified-good (map (fn [[good disqualified]] (and good (not disqualified))) value-and-disqualified)
        ]
    ;; (prn same-pairs)
    ;; (prn same-pairs-disqualified)
    (some (fn [[good disq1 disq2]]
            (and good (not disq1) (not disq2))) triplets)))
;; (and (some true? same-pairs)
;;      (not (some (fn [[a b]] (and a b)) same-pair-pairs)))
; bad
(has-adjacent-digits? [1 1 2 2 2])
(has-adjacent-digits? [1 1 1 3 3 3])

(has-adjacent-digits? [1 1 1])
(has-adjacent-digits? [1 1 2 2 3 4 4 4])
(has-adjacent-digits? [1 2 3])

(defn digits-increase? [digits]
  (apply <= digits))

(digits-increase? [1 2 3])
(digits-increase? [4 2 3])

(defn get-digits [number]
  (loop [n number result []]
    (if (zero? n)
      result
      (recur (quot n 10) (cons (mod n 10) result)))))

(defn match? [value]
  (let [digits (get-digits value)]
    (and (has-adjacent-digits? digits)
         (digits-increase? digits))))

(defn main []
  (let [input (read-line)
        [low high] (map #(Integer. %) (split input #"-"))
        range (range low high)
        matches (filter match? range)]
    (prn (count matches))))

(with-in-str (slurp "input")
  (main))
