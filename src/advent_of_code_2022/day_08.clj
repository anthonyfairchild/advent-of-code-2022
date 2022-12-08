(ns advent-of-code-2022.day-08
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn read-input [file-name]
  (-> file-name io/resource slurp s/split-lines
      (->> (mapv (fn [line] (-> line (s/split #"")
                                (->> (mapv #(Integer/parseInt %)))))))))

(def test-input (read-input "day_08_test_input.txt"))
(def input (read-input "day_08.txt"))

(defn at [input x y] (get (get input y) x))

(defn adjacent-and-to-edge [input x y] 
  (->> [(for [ox (range (inc x) (count input))]
          [ox y])
        (reverse (for [ox (range x)]
                   [ox y]))
        (for [oy (range (inc y) (count input))]
          [x oy])
        (reverse (for [oy (range y)]
                   [x oy]))]
       (map (fn [idxs] (map (fn [[x y]] (at input x y)) idxs)))))

(defn visible? [input [x y]]
  (let [me (at input x y)
        hss (adjacent-and-to-edge input x y)]
    (some (fn [hs] (every? (fn [h] (< h me)) hs)) hss)))

(defn all-indices [input]
  (for [x (range (count input))
        y (range (count input))]
    [x y]))

(defn solve1 [input]
  (->> input all-indices
       (filter #(visible? input %))
       count))

(solve1 test-input)
;; => 21

(solve1 input)
;; => 1676

(defn score-row [me hs]
  (reduce (fn [score h]
            (if (>= h me)
              (reduced (inc score))
              (inc score)))
          0 hs))

(defn score[input [x y]]
  (let [me (at input x y)
        hss (adjacent-and-to-edge input x y)]
    (reduce (fn [s hs]
              (* s (score-row me hs)))
            1 hss)))

(defn solve2 [input]
  (->> input all-indices
       (map #(score input %))
       (apply max)))

(solve2 test-input)
;; => 8

(solve2 input)
;; => 313200
