(ns advent-of-code-2022.day-06
  (:require [clojure.java.io :as io]))

(defn read-input [file-name]
  (-> file-name
      io/resource
      slurp))

(def test-input (read-input "day_06_test_input.txt"))
(def input (read-input "day_06.txt"))

(defn solve [input n]
  (->> input 
       (partition n 1)
       (map-indexed vector)
       (filter (fn [[_ ns]] (= (count (set ns)) n)))
       first first
       (+ n)))

(solve test-input 4)
;; => 7

(solve input 4)
;; => 1766

(solve test-input 14)
;; => 19

(solve input 14)
;; => 2383
