(ns advent-of-code-2022.day-07
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn read-input [file-name]
  (-> file-name
      io/resource
      slurp
      s/split-lines))

(def test-input (read-input "day_07_test_input.txt"))
(def input (read-input "day_07.txt"))

(defn parse-cd [line] (some->> line (re-find #"^\$ cd (.+)$") (drop 1) first))
(defn parse-file [line] (some->> (re-find #"^(\d+) (.+)$" line) (drop 1)))

(defn paths [dirs]
  (loop [[first & rest] (reverse dirs)
         acc []]
    (if first
      (recur rest (conj acc (reverse (apply vector first rest))))
      acc)))

(defn process-cli [input]
  (->> input
       (reduce (fn [[dirs state] line]
                 (if-let [dir (parse-cd line)]
                   (case dir
                     ".." [(pop dirs) state]
                     [(conj dirs dir) state])
                   (if-let [[size _] (parse-file line)]
                     (let [size (Long/parseLong size)]
                       [dirs (reduce (fn [s d]
                                       (update s d #(+ (or % 0) size)))
                                     state (paths dirs))]) 
                     [dirs state])))
               [[] {}])
       second))

(defn solve1 [input]
  (->> input process-cli
       (filter (fn [[_ size]] (<= size 100000)))
       (map second)
       (reduce +)))

(solve1 test-input)
;; => 95437

(solve1 input)
;; => 1648397

(defn solve2 [input]
  (let [dir-sizes (process-cli input)
        free-space (- 70000000 (get dir-sizes ["/"]))]
    (->> dir-sizes
         (sort-by second)
         (filter (fn [[_ size]] (> (+ size free-space)
                                   30000000)))
         first
         second)))

(solve2 test-input)
;; => 24933642

(solve2 input)
;; => 1815525
