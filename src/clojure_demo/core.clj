; Place this code in core.clj to run
;
;$ lein run
;{:name back, :size 10}
;nil
;
;$ lein run 4
;{:name abdomen, :size 6}
;{:name right-knee, :size 2}
;{:name right-shoulder, :size 3}
;{:name left-thigh, :size 4}
;nil
;
;$ lein run 6 4
;{:name 4-lower-leg, :size 3}
;{:name abdomen, :size 6}
;{:name 4-hand, :size 2}
;{:name back, :size 10}
;{:name abdomen, :size 6}
;{:name chest, :size 10}
;nil

(ns clojure-demo.core
  (:gen-class))

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn alien-matching-parts
  [part]
  [{:name (clojure.string/replace (:name part) #"^left-" "right-")
    :size (:size part)},
   {:name (clojure.string/replace (:name part) #"^left-" "upper-")
    :size (:size part)},
   {:name (clojure.string/replace (:name part) #"^left-" "lower-")
    :size (:size part)},
   {:name (clojure.string/replace (:name part) #"^left-" "middle-")
    :size (:size part)}])

(defn any-creature-matching-parts
  "Return a list of parts based on the original part and the n-fold symmetry"
  [part n]
  (map
   #(hash-map :name (clojure.string/replace (:name part) #"^left-" (str (inc %) "-"))
     :size          (:size part))
   (take n (range))))


(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce
   (fn [final-body-parts part]
     (into final-body-parts (set [part (matching-part part)])))
   []
   asym-body-parts))

(defn alien-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce
   (fn [final-body-parts part]
     (into final-body-parts (set (conj (alien-matching-parts part) part))))
   []
   asym-body-parts))

(defn any-creature-symmetrize-body-parts
  "Return a function that symmetrize body parts n-fold"
  [asym-body-parts n]
  (reduce
   (fn [final-body-parts part]
     (into final-body-parts (set (any-creature-matching-parts part n))))
   []
   asym-body-parts))

(defn hit
  ([asym-body-parts]
   (let [sym-parts          (alien-symmetrize-body-parts asym-body-parts)
         body-part-size-sum (reduce + (map :size sym-parts))
         target             (rand body-part-size-sum)]
     (loop [[part & remaining] sym-parts
            accumulated-size   (:size part)]
       (if (> accumulated-size target)
         part
         (recur remaining (+ accumulated-size (:size (first remaining))))))))

  ([asym-body-parts creature-symmetry]
   (let [sym-parts          (any-creature-symmetrize-body-parts asym-body-parts creature-symmetry)
         body-part-size-sum (reduce + (map :size sym-parts))
         target             (rand body-part-size-sum)]
     (loop [[part & remaining] sym-parts
            accumulated-size   (:size part)]
       (if (> accumulated-size target)
         part
         (recur remaining (+ accumulated-size (:size (first remaining)))))))))

(defn hit-n-times
  ([n]
   (loop [hit-counter 1]
     (println (hit asym-hobbit-body-parts))
     (if (< hit-counter, n)
       (recur (inc hit-counter)))))

  ([n creature-symmetry]
   (loop [hit-counter 1]
     (println (hit asym-hobbit-body-parts creature-symmetry))
     (if (< hit-counter, n)
       (recur (inc hit-counter))))))

(defn -main
  "I don't do a whole lot ... yet."
  ([]
   (-main 1))

  ([n]
   (println (hit-n-times (Integer. n))))

  ([n creature-symmetry]
   (println (hit-n-times (Integer. n) (Integer. creature-symmetry)))))
