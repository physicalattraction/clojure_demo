(ns chapter5-exercises.core-test
  (:require [clojure.test :refer :all]
            [chapter5-exercises.core :refer :all]))

(deftest attr-returns-correct-result
  (let [character
        {:name       "Smooches McCutes"
         :attributes {:intelligence 10
                      :strength     4
                      :dexterity    5}}]
    (is (= ((attr :intelligence) character) 10))
    (is (= ((attr :strength) character) 4))
    (is (= ((attr :dexterity) character) 5))
    (is (= ((attr :wisdom) character) nil))))

(deftest my-comp-returns-correct-result
  (is (= ((my-comp inc) 3) 4))
  (is (= ((my-comp inc #(+ % 3)) 3) 7))
  (is (= ((my-comp inc #(+ % 3) *) 2 3) 10)))

(deftest my-assoc-in-returns-correct-result
  (let [users [{:name "James" :age 26} {:name "John" :age 43}]]
    (is
     (= (my-assoc-in users [1 :age] 44)
        [{:name "James" :age 26} {:name "John" :age 44}]))
    (is
     (= (my-assoc-in users [1 :password] "nhoJ")
        [{:name "James" :age 26} {:name "John" :age 43 :password "nhoJ"}]))
    (is
     (= (my-assoc-in users [2] {:name "Jack" :age 19})
        [{:name "James", :age 26} {:name "John", :age 43} {:name "Jack", :age 19}]))))

(deftest my-update-in-returns-correct-result
  (let [users [{:name "James" :age 26} {:name "John" :age 43}]]
    (is
     (= (my-update-in users [1 :age] inc)
        [{:name "James" :age 26} {:name "John" :age 44}]))
    (is
     (= (my-update-in users [1 :password] (fn [input] "nhoJ"))
        [{:name "James" :age 26} {:name "John" :age 43 :password "nhoJ"}]))
    (is
     (= (my-update-in users [1 :name] #(str % " the Great"))
        [{:name "James" :age 26} {:name "John the Great" :age 43}]))
    (is
     (= (my-update-in users [2] assoc :name "Jack" :age 19))
     [{:name "James", :age 26} {:name "John", :age 43} {:name "Jack", :age 19}])))
