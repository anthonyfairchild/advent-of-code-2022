(ns advent-of-code-2022.day-02
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn read-input [file-name]
  (-> file-name
      io/resource
      slurp
      (s/split #"\n")
      (->> (mapv #(s/split % #" ")))))

(def test-input (read-input "day_02_test_input.txt"))
(def input (read-input "day_02.txt"))

(def abcs {"A" 1
           "B" 2
           "C" 3})

(def xyzs {"X" 1
           "Y" 2
           "Z" 3})

(defn turn1 [abc xyz]
  (let [res [3 0 6]]
    (+ (xyzs xyz) 
       (res (mod (- (abcs abc) (xyzs xyz)) 3)))))

(defn solve1 [input]
  (->> input
       (map #(apply turn1 %))
       (apply +)))

(solve1 test-input)
;; => 15

(solve1 input)
;; => 9241

(defn turn2 [abc xyz]
  (let [res [0 3 6]]
    (+ (inc (mod (+ (abcs abc) (xyzs xyz)) 3))
       (res (dec (xyzs xyz))))))

(defn solve2 [input]
  (->> input
       (map #(apply turn2 %))
       (apply +)))

(solve2 test-input)
;; => 12

(solve2 input)
;; => 14610
