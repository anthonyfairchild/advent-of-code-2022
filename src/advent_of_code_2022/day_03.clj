(ns advent-of-code-2022.day-03
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as s]))

(defn read-input [file-name]
  (-> file-name
      io/resource
      slurp
      s/split-lines))

(def test-input (read-input "day_03_test_input.txt"))
(def input (read-input "day_03.txt"))

(defn priority [c]
  (let [p (inc (- (int c) (int \a)))]
    (if (< p 0) (+ p 26 5 27) p)))

(defn solve1 [input]
  (->> input
       (map (fn [l] (split-at (/ (count l) 2) l)))
       (map #(map set %))
       (map (fn [[a b]] (set/intersection a b)))
       (map first)
       (map priority)
       (reduce +)))

(solve1 test-input)
;; => 157

(solve1 input)
;; => 8109

(defn solve2 [input]
  (->> input
       (partition 3)
       (map #(map set %))
       (map (fn [[a b c]] (set/intersection a b c)))
       (map first)
       (map priority)
       (reduce +)))

(solve2 test-input)
;; => 70

(solve2 input)
;; => 2738
