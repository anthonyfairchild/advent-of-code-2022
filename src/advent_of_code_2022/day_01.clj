(ns advent-of-code-2022.day-01
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn read-input [file-name]
  (-> file-name
      io/resource
      slurp
      (s/split #"\n\n")
      (->> (map (fn [s] (->> (s/split s #"\n")
                             (map (fn [s] (Integer/parseInt s)))))))))

(defn solve1 [input]
 (let [sums (map (fn [ls] (apply + ls)) input)]
   (apply max sums)))

(defn solve2 [input]
  (let [sums (map (fn [ls] (apply + ls)) input)]
    (->> sums 
         (sort >)
         (take 3) 
         (apply +))))
#_
(-> "day_01.txt"
    read-input
    solve1)

#_
(-> "day_01.txt"
    read-input
    solve2)


