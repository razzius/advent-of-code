(ns password)

(require '[clojure.string :refer [split]])

(defn adjacent-pairs [l]
  (map vector (butlast l) (rest l)))

(defn has-adjacent-digits? [digits]
  (let [pairs (adjacent-pairs digits)
        same-pairs (map (fn [[a b]] (= a b)) pairs)
        same-pair-pairs (adjacent-pairs same-pairs)
        same-pairs-disqualified (map (fn [[a b]] (and a b)) same-pair-pairs)
        left-aligned-disqualified (concat '(false) same-pairs-disqualified)
        right-aligned-disqualified (concat same-pairs-disqualified '(false))
        triplets (map vector same-pairs left-aligned-disqualified right-aligned-disqualified)]
    (some (fn [[good disq1 disq2]]
            (and good (not disq1) (not disq2))) triplets)))

(defn digits-increase? [digits]
  (apply <= digits))

(defn get-digits [number]
  (loop [n number result []]
    (if (zero? n)
      result
      (recur (quot n 10) (cons (mod n 10) result)))))

(defn match? [value]
  (let [digits (get-digits value)]
    (and (has-adjacent-digits? digits)
         (digits-increase? digits))))

(defn -main []
  (let [input (read-line)
        [low high] (map #(Integer. %) (split input #"-"))
        range (range low high)
        matches (filter match? range)]
    (prn (count matches))))

(-main)
