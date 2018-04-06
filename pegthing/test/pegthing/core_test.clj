(ns pegthing.core-test
  (:require [clojure.test :refer :all]
            [pegthing.core :refer :all]))

;;;;
;; Create the board
;;;;

(deftest tri-returns-correct-result
  (testing "The function tri returns a seq of triangular numbers"
           (is (= [1 3 6 10 15 21] (take 6 tri)))))

(deftest triangular?-returns-correct-result
  (is (= (triangular? 1) true))
  (is (= (triangular? 2) false))
  (is (= (triangular? 3) true)))

(deftest row-tri-returns-correct-result
  (is (= (row-tri 1) 1))
  (is (= (row-tri 2) 3))
  (is (= (row-tri 3) 6)))

(deftest row-num-returns-correct-result
  (is (= (row-num 2) 2))
  (is (= (row-num 4) 3))
  (is (= (row-num 6) 3)))

(deftest in-bounds?-returns-correct-result
  (is (= (in-bounds? 15 1 7 15) true))
  (is (= (in-bounds? 15 7 16) false)))

(deftest connect-returns-correct-result
  ; Make a double connection
  (is (= (connect {} 15 1 2 4) {4 {:connections {1 2}}, 1 {:connections {4 2}}}))
  ; Add to an existing board
  (is
   (= (connect {4 {:connections {1 2}}, 1 {:connections {4 2}}} 15 4 5 6)
      {6 {:connections {4 5}}, 1 {:connections {4 2}}, 4 {:connections {6 5, 1 2}}}))
  ; Do not add positions out of bounds
  (is (= (connect {} 10 13 14 15) {}))
  ; Note that this function does not check for erroneous connections
  (is (= (connect {} 15 1 2 3) {3 {:connections {1 2}}, 1 {:connections {3 2}}})))

(deftest connect-right-returns-correct-result
  ; Do not add connection if there is no connection to the right
  (is (= (connect-right {} 15 2) {}))
  ; Make a double connection to the right
  (is (= (connect-right {} 15 4) {4 {:connections {6 5}}, 6 {:connections {4 5}}})))

(deftest connect-down-left-returns-correct-result
  ; Do not add connection if there is no connection to the down-left
  (is (= (connect-down-left {} 15 11) {}))
  ; Make a double connection to the down-left
  (is
   (= (connect-down-left {} 15 2) {2 {:connections {7 4}}, 7 {:connections {2 4}}})))

(deftest connect-down-right-returns-correct-result
  ; Do not add connection if there is no connection to the down-right
  (is (= (connect-down-right {} 15 11) {}))
  ; Make a double connection to the down-right
  (is
   (= (connect-down-right {} 15 2) {2 {:connections {9 5}}, 9 {:connections {2 5}}})))

(deftest add-pos-returns-correct-result
  ; Make all connections to all three directions (excluding the reverse, which are already created)
  (is
   (= (add-pos {} 15 4)
      {4  {:pegged true, :connections {13 8, 11 7, 6 5}},
       6  {:connections {4 5}},
       11 {:connections {4 7}},
       13 {:connections {4 8}}})))

(deftest new-board-returns-correct-result
  (is
   (= (new-board 2)
      {1     {:pegged true},
       2     {:pegged true},
       3     {:pegged true},
       :rows 2}))
  (is
   (= (new-board 3)
      {1     {:pegged true, :connections {6 3, 4 2}},
       2     {:pegged true},
       3     {:pegged true},
       4     {:pegged true, :connections {6 5, 1 2}},
       5     {:pegged true},
       6     {:pegged true, :connections {4 5, 1 3}},
       :rows 3}))
  (is
   (= (new-board 4)
      {1     {:pegged true, :connections {6 3, 4 2}},
       2     {:pegged true, :connections {9 5, 7 4}},
       3     {:pegged true, :connections {10 6, 8 5}},
       4     {:pegged true, :connections {6 5, 1 2}},
       5     {:pegged true},
       6     {:pegged true, :connections {4 5, 1 3}},
       7     {:pegged true, :connections {9 8, 2 4}},
       8     {:pegged true, :connections {10 9, 3 5}},
       9     {:pegged true, :connections {7 8, 2 5}},
       10    {:pegged true, :connections {8 9, 3 6}},
       :rows 4}))
  (is
   (= (new-board 5)
      {1     {:pegged true, :connections {6 3, 4 2}}
       2     {:pegged true, :connections {9 5, 7 4}},
       3     {:pegged true, :connections {10 6, 8 5}},
       4     {:pegged true, :connections {13 8, 11 7, 6 5, 1 2}},
       5     {:pegged true, :connections {14 9, 12 8}},
       6     {:pegged true, :connections {15 10, 13 9, 4 5, 1 3}},
       7     {:pegged true, :connections {9 8, 2 4}},
       8     {:pegged true, :connections {10 9, 3 5}},
       9     {:pegged true, :connections {7 8, 2 5}},
       10    {:pegged true, :connections {8 9, 3 6}},
       11    {:pegged true, :connections {13 12, 4 7}},
       12    {:pegged true, :connections {14 13, 5 8}},
       13    {:pegged true, :connections {15 14, 11 12, 6 9, 4 8}},
       14    {:pegged true, :connections {12 13, 5 9}},
       15    {:pegged true, :connections {13 14, 6 10}},
       :rows 5})))


;;;;
;; Move pegs
;;;;

(deftest pegged?-returns-correct-result
  (let [board {1     {:pegged true},
               2     {:pegged true},
               3     {:pegged false},
               :rows 2}]
    (is (= (pegged? board 1) true))
    (is (= (pegged? board 3) false))))

(deftest valid-moves-returns-correct-result
  (let [board (remove-peg (remove-peg (new-board 5) 4) 13)]
    (is (= (valid-moves board 1) {4 2}))
    (is (= (valid-moves board 2) {}))
    (is (= (valid-moves board 4) {}))
    (is (= (valid-moves board 6) {4 5, 13 9}))))

(deftest valid-move?-returns-correct-result
  (let [board (remove-peg (remove-peg (new-board 5) 4) 13)]
    (is (= (valid-move? board 2 4) nil))
    (is (= (valid-move? board 1 4) 2))))

(deftest remove-peg-returns-correct-result
  (let [board (new-board 2)]
    (is
     (= (remove-peg board 1)
        {1 {:pegged false}, 2 {:pegged true}, 3 {:pegged true}, :rows 2}))
    (is
     (= (remove-peg (remove-peg board 1) 2)
        {1 {:pegged false}, 2 {:pegged false}, 3 {:pegged true}, :rows 2}))
    (is
     (= (remove-peg (remove-peg board 1) 1)
        {1 {:pegged false}, 2 {:pegged true}, 3 {:pegged true}, :rows 2}))))

(deftest place-peg-returns-correct-result
  (let [board (remove-peg (new-board 2) 3)]
    (is
     (= (place-peg board 1) board))
    (is
     (= (place-peg board 3) (new-board 2)))))

(deftest move-peg-returns-correct-result
  (let [board-before {1     {:pegged true},
                      2     {:pegged true},
                      3     {:pegged false},
                      :rows 2}
        board-after  {1     {:pegged false},
                      2     {:pegged true},
                      3     {:pegged true},
                      :rows 2}]
    (is (= (move-peg board-before 1 3)))))

(deftest make-move-returns-correct-result
  (let [board-before (remove-peg (new-board 5) 5)
        board-after  (remove-peg (remove-peg (new-board 5) 8) 12)]
    (is (= (make-move board-before 12 5) board-after))))

(deftest can-move?-returns-correct-result
  (let [board-can-move    (remove-peg (new-board 5) 5)
        board-cannot-move {1     {:pegged false, :connections {6 3, 4 2}},
                           2     {:pegged true, :connections {9 5, 7 4}},
                           3     {:pegged false, :connections {10 6, 8 5}},
                           4     {:pegged false, :connections {6 5, 1 2}},
                           5     {:pegged false},
                           6     {:pegged false, :connections {4 5, 1 3}},
                           7     {:pegged false, :connections {9 8, 2 4}},
                           8     {:pegged false, :connections {10 9, 3 5}},
                           9     {:pegged false, :connections {7 8, 2 5}},
                           10    {:pegged true, :connections {8 9, 3 6}},
                           :rows 4}]
    (is (= (can-move? board-can-move) {5 8}))
    (is (= (can-move? board-cannot-move) nil))))


;;;;
;; Represent board textually and print it
;;;;

(deftest render-pos-returns-correct-result
  (let [board (remove-peg (new-board 5) 5)]
    (is (= (render-pos board 1) (str "a" (colorize "0" :blue))))
    (is (= (render-pos board 5) (str "e" (colorize "-" :red))))))

(deftest row-positions-returns-correct-result
  (is (= (row-positions 1)) [1])
  (is (= (row-positions 3)) [4 5 6]))

(deftest row-padding-returns-correct-result
  (is (= (row-padding 1 3) "   "))
  (is (= (row-padding 1 5) "      "))
  (is (= (row-padding 3 5) "   ")))

(deftest render-row-returns-correct-result
  (let [board (remove-peg (new-board 5) 5)
        expected-row (str "   d" (colorize "0" :blue) " e" (colorize "-" :red) " f" (colorize "0" :blue))]
    (is (= (render-row board 3) expected-row))))


;;;;
;; Interaction
;;;;

(deftest letter->pos-returns-correct-result
  (let [board (new-board 5)]
    (is (= (letter->pos "e") 5))))

(deftest characters-as-strings-returns-correct-result
  (is (= (characters-as-strings "a b") ["a" "b"]))
  (is (= (characters-as-strings "a b   1 A .   B") ["a" "b" "A" "B"])))

(deftest max-rows-returns-correct-result
  (is (= (max-rows ["a" "b" "c"]) 2))
  (is (= (max-rows letters) 6)))
