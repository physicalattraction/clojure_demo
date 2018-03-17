(ns clojure-demo.core
  (:require [clojure-demo.hobbit_smasher :refer :all]))

(defn -main
  "I don't do a whole lot ... yet."
  ([]
   (-main 1))

  ([n]
   (println (hit-n-times (Integer. n))))

  ([n creature-symmetry]
   (println (hit-n-times (Integer. n) (Integer. creature-symmetry)))))
