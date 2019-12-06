(ns wires)

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

(defn step-position [direction {traveled :traveled [x y] :position}]
  (let [position (cond (= direction \R) [(+ x 1) y]
                       (= direction \U) [x (+ y 1)]
                       (= direction \L) [(- x 1) y]
                       (= direction \D) [x (- y 1)])]
    {:position position :traveled (inc traveled)}))

;good
;; (step-position \D {:position [0 0] :traveled 0})

(defn between [{direction :direction distance :distance traveled :traveled} position]
  (take (+ 1 distance) (iterate (partial step-position direction) {:position position :traveled traveled})))

;; (take 2 (iterate (partial step-position \U 0) [0 0]))

;good
;; (between {:direction \U :distance 2 :traveled 0} [0 0])

;; (def entries (between {:direction \U :distance 2 :traveled 0} [0 0]))

(defn evaluate-one-wire [{nodes :nodes position :position traveled :traveled}
                         {direction :direction distance :distance}]
  (let [stuff-between (between {:direction direction :distance distance :traveled traveled} position)
        records (map (fn [{position :position traveled :traveled}] {position traveled}) stuff-between)]
    {:nodes (into nodes (reverse records)) ; reverse to take the first with that position
     :position (-> stuff-between last :position)
     :traveled (+ traveled distance)}))

;; (evaluate-one-wire )
(defn evaluate-wire [instructions]
  (reduce evaluate-one-wire {:nodes {} :position [0 0] :traveled 0} instructions))

(defn key-set [map]
  (set (keys map)))

(defn main []
  (let [first-wire-input (read-line)
        second-wire-input (read-line)
        first-wire-list (input-to-list first-wire-input)
        second-wire-list (input-to-list second-wire-input)
        first-wire-map (:nodes (evaluate-wire first-wire-list))
        second-wire-map (:nodes (evaluate-wire second-wire-list))
        both (intersection (key-set first-wire-map) (key-set second-wire-map))
        costs (map #(+ (get first-wire-map %) (get second-wire-map %)) both)
        not-starting (filter pos? costs)
        closest (apply min not-starting)]
    (prn closest)))

; (with-in-str "U3,R1
; U1" (main))
; (with-in-str example (main))

;; (main)

(def ans-610 "R75,D30,R83,U83,L12,D49,R71,U7,L72
U62,R66,U55,R34,D71,R55,D58,R83")

(def simple "R5,U5,L2
U3,R10")

(def example "R8,U5,L5,D3
U7,R6,D4,L4")

; (with-in-str ans-610 (main))
(with-in-str (slurp "input")
  (main))

;; (defn read-one-line-from-file [filename]
;;   (-> filename slurp (split #"\n") first))

;; (with-in-str (read-one-line-from-file "input")
;;   (main))

; wire goes up to ...
; wire goes right to ...
