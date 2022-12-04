(ns advent-of-code-2022.day-04
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn read-input [file-name]
  (-> file-name io/resource slurp s/split-lines
      (->> (map (fn [line]
                  (-> line (s/split #"[,\-]")
                      (->> (map #(Integer/parseInt %)))))))))

(def test-input (read-input "day_04_test_input.txt"))
(def input (read-input "day_04.txt"))

(defn fully-contained [a1 b1 a2 b2]
  (or (and (>= a2 a1) (<= b2 b1))
      (and (>= a1 a2) (<= b1 b2))))

(defn solve1 [input]
  (->> input 
       (map #(apply fully-contained %))
       (filter identity)
       count))

(solve1 test-input)
;; => 2

(solve1 input)
;; => 475

(defn overlap-at-all [a1 b1 a2 b2]
  (and (<= a2 b1)
       (>= b2 a1)))

(defn solve2 [input]
  (->> input
       (map #(apply overlap-at-all %))
       (filter identity)
       count))

(solve2 test-input)
;; => 4

(solve2 input)
;; => 825
