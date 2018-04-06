(ns chapter5-exercises.core
  (:gen-class))

;1. You used (comp :intelligence :attributes) to create a function that returns a character’s intelligence.
;Create a new function, attr, that you can call like (attr :intelligence) and that does the same thing.
(defn attr
  [attribute]
  (comp attribute :attributes))

;2. Implement the comp function.
(defn my-comp
  ;If there is only one function as argument, return that function
  ([f]
   f)
  ;If there are multiple functions as arguments, apply the first to the composition of all the others
  ([f & fs]
   (fn [& args] (f (apply (apply my-comp fs) args)))))

;3. Implement the assoc-in function. Hint: use the assoc function and define its parameters as [m [k & ks] v].
(defn my-assoc-in
  [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (let [mk (get m k)]
      (map #(if (= % mk) (my-assoc-in mk ks v) %) m))))

;4. Look up and use the update-in function.
;See test cases

;5. Implement update-in
(defn my-update-in
  [m ks f & args]
  (let [current-value (get-in m ks)
        new-value     (apply f current-value args)]
    (assoc-in m ks new-value)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
