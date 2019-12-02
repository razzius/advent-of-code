(defn fuel [mass]
  (-> mass
      (/ 3)
      Math/floor
      (- 2)
      int))

(println (apply + (map #(-> % Integer. fuel) (line-seq (java.io.BufferedReader. *in*)))))
