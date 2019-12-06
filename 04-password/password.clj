(require '[clojure.string :refer [split]])

(defn adjacent-pairs [l]
  (map vector (butlast l) (rest l)))

(adjacent-pairs [1 2 3])

(defn has-adjacent-digits? [digits]
  (let [pairs (adjacent-pairs digits)]
    (some true? (map (fn [[a b]] (= a b)) pairs))))

(has-adjacent-digits? [1 1 3])
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
