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

(defn turn1 [abc xyz]
  (let [s {"X" 1
           "Y" 2
           "Z" 3}]
    (cond
      ;; win
      (or (and (= abc "A") (= xyz "Y"))
          (and (= abc "B") (= xyz "Z"))
          (and (= abc "C") (= xyz "X")))
      (+ (s xyz) 6)
      
      ;; draw
      (or (and (= abc "A") (= xyz "X"))
          (and (= abc "B") (= xyz "Y"))
          (and (= abc "C") (= xyz "Z")))
      (+ (s xyz) 3)
      
      :else (s xyz))))

(defn solve1 [input]
  (->> input
       (map #(apply turn1 %))
       (apply +)))

(solve1 test-input)
;; => 15

(solve1 input)
;; => 9241


(defn turn2 [abc xyz]
  (let [s {"X" 1
           "Y" 2
           "Z" 3}
        win {"A" "Y"
             "B" "Z"
             "C" "X"}
        draw {"A" "X"
              "B" "Y"
              "C" "Z"}
        lose {"A" "Z"
              "B" "X"
              "C" "Y"}]
    (cond
      ;; win
      (= xyz "Z")
      (+ (s (win abc)) 6)
      
      ;; draw
      (= xyz "Y")
      (+ (s (draw abc)) 3)
      
      :else
      (s (lose abc)))))

(defn solve2 [input]
  (->> input
       (map #(apply turn2 %))
       (apply +)))

(solve2 test-input)
;; => 15

(solve2 input)
;; => 14610




test-input
input