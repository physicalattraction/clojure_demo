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

(ns clojure-demo.improved_hobbit_smasher
  (:gen-class))

(def asym-body-parts [{:name "head" :size 3}
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


(defn matching-parts
  "Return a list of parts based on the original part and the n-fold symmetry"
  [part creature-symmetry]
  (if (= creature-symmetry 2)
    [part
     {:name (clojure.string/replace (:name part) #"^left-" "right-")
      :size (:size part)}]
    (map
     #(hash-map :name (clojure.string/replace (:name part) #"^left-" (str (inc %) "-"))
       :size          (:size part))
     (take creature-symmetry (range)))))


(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts creature-symmetry]
  (reduce
   (fn [final-body-parts part]
     (into final-body-parts (set (matching-parts part creature-symmetry))))
   []
   asym-body-parts))

(defn hit
  "Hit a creature given the asymmetric body parts and the degree of symmetry"
  [asym-body-parts creature-symmetry]
  (let [sym-parts          (symmetrize-body-parts asym-body-parts creature-symmetry)
        body-part-size-sum (reduce + (map :size sym-parts))
        target             (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size   (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(defn hit-n-times
  "Hit a creature a given amount of times given the asymmetric body parts and the degree of symmetry"
  [hit-final-count asym-body-parts creature-symmetry]
  (loop [hit-counter 1]
    (println (hit asym-body-parts creature-symmetry))
    (if (< hit-counter, hit-final-count)
      (recur (inc hit-counter)))))
