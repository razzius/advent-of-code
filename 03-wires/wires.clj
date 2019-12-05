(require '[clojure.string :refer [split]]
         '[clojure.set :refer [union intersection]])

(defn char-list-to-int [chars]
  (Integer. (apply str chars)))

(defn parse-instructon [instruction]
  (let [[str-direction & str-distance] instruction]
    {:direction str-direction :distance (char-list-to-int str-distance)}))

(defn input-to-list [input]
  (let [instructions (split input #",")]
    (map parse-instructon instructions)))

(defn step-wire [{direction :direction distance :distance} [x y]]
  (cond (= direction \R) [(+ x distance) y]
        (= direction \U) [x (+ y distance)]
        (= direction \L) [(- x distance) y]
        (= direction \D) [x (- y distance)]))

(defn between [[startx starty] [endx endy]]
  (let [[lessx morex] (sort [startx endx])
        [lessy morey] (sort [starty endy])]
    (set (if (= startx endx)
      (for [y (range lessy morey)] [startx y])
      (for [x (range lessx morex)] [x starty])))))

(defn evaluate-one-wire [{nodes :nodes position :position}
                         instruction]
  (let [end-position (step-wire instruction position)
        stuff-between (between position end-position)]
    {:nodes (union nodes stuff-between) :position end-position}))

(defn evaluate-wire [instructions]
  (reduce evaluate-one-wire {:nodes #{} :position [0 0]} instructions))

(defn distance [[x y]]
  (+ (Math/abs x) (Math/abs y)))

(defn main []
  (let [first-wire-input (read-line)
        second-wire-input (read-line)
        first-wire-list (input-to-list first-wire-input)
        second-wire-list (input-to-list second-wire-input)
        first-wire-set (:nodes (evaluate-wire first-wire-list))
        second-wire-set (:nodes (evaluate-wire second-wire-list))
        both (intersection first-wire-set second-wire-set)
        closest (apply min-key distance both)]
    (prn (distance closest))))

(with-in-str (slurp "input")
  (main))

;; (defn read-one-line-from-file [filename]
;;   (-> filename slurp (split #"\n") first))

;; (with-in-str (read-one-line-from-file "input")
;;   (main))

; wire goes up to ...
; wire goes right to ...
