(ns clojure-demo.core
  (:require [clojure-demo.improved_hobbit_smasher :refer :all]))

(defn -main
  "Hit a creature"
  ([]
   (println (hit-n-times 1 asym-body-parts 2)))

  ([hit-final-count]
   (println (hit-n-times (Integer. hit-final-count) asym-body-parts 2)))

  ([hit-final-count creature-symmetry]
   (println (hit-n-times (Integer. hit-final-count) asym-body-parts (Integer. creature-symmetry)))))
