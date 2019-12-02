(defn fuel-required [mass]
  (-> mass
      (/ 3)
      Math/floor
      (- 2)
      int))

(defn fuel [mass]
  (let [necessary (fuel-required mass)]
    (println necessary mass)
    (if (> necessary 2)
      (+ necessary (fuel necessary))
      (max 0 necessary))))

(let [lines (line-seq (java.io.BufferedReader. *in*))]
  (println (apply + (map #(-> % Integer. fuel) lines))))
