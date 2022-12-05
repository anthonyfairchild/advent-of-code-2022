(ns advent-of-code-2022.day-05
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn read-stack-line [line]
  (-> line
      (concat " ")
      (->> (apply str)
           (partition 4)
           (map (fn [s] (-> s (->> (apply str) s/trim)
                            (s/replace "[" "")
                            (s/replace "]" "")))))))

(defn stack-add [stacks i c]
  (update-in stacks [i] #(conj (or % []) c)))

(defn stack-move [stacks from to]
  (let [c (last (get stacks from))
        stacks (update-in stacks [from] #(pop %))]
    (stack-add stacks to c)))

(defn stack-bulk-move [stacks n from to]
  (reduce (fn [stacks _] (stack-move stacks from to) ) stacks (range n)))

(defn read-stacks [s]
  (->> s
       s/split-lines
       drop-last
       (mapv read-stack-line)
       reverse
       (reduce (fn [acc line]
                 (->> line
                      (map-indexed vector)
                      (reduce (fn [acc [i c]]
                                (if (not-empty c) (stack-add acc (inc i) c) acc)) acc)))
               {})))

(defn read-move-line [line]
  (let [[_ n from to] (re-find (re-matcher
                                   #"move (\d+) from (\d+) to (\d+)"
                                   line))]
    (mapv #(Integer/parseInt %) [n from to])))

(defn read-moves [str]
  (->> str
       s/split-lines
       (map read-move-line)))

(defn read-input [file-name]
  (let [str (-> file-name io/resource slurp)
        [a b] (s/split str #"\n\n")]
    [(read-stacks a) (read-moves b)]))

(def test-input (read-input "day_05_test_input.txt"))
(def input (read-input "day_05.txt"))

(defn solve1 [[stacks moves]]
  (->> (reduce (fn [stacks [n from to]] (stack-bulk-move stacks n from to)) stacks moves)
       (sort-by first)
       (map second)
       (map last)
       (apply str)))

(solve1 test-input)
;; => "CMZ"

(solve1 input)
;; => "FRDSQRRCD"


(defn stack-bulk-move2 [stacks n from to]
  (let [to-move (take-last n (stacks from))
        stacks (update-in stacks [from] #(->> % (drop-last n) vec))]
    (reduce (fn [stacks c] (stack-add stacks to c)) stacks to-move)))

(defn solve2 [[stacks moves]]
  (->> (reduce (fn [stacks [n from to]] (stack-bulk-move2 stacks n from to)) stacks moves)
       (sort-by first)
       (map second)
       (map last)
       (apply str)))

(solve2 test-input)
;; => "MCD"

(solve2 input)
;; => "HRFTQVWNN"
