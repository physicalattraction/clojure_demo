(defn titleize
  [topic]
  (str topic " for the Brave and True"))

(def my_vector  ["Hamsters" "Ragnarok"])

(map titleize my_vector)

(def my_list '("Empathy" "Decorating"))

(map titleize my_list)

(def my_set #{"Elbows" "Soap Carving"})

(map titleize my_set)

(def my_map {:uncomfortable-thing "Winking" :comfortable-thing "Hugging"})

;Create an anonymous function from another function
(map #(titleize (second %)) my_map)

; Get the first element of the sequence
(first my_vector)

; Get everything after the first element of the sequence
(rest my_vector)

; Construct a new sequence by adding element in front of the existing sequence
(def element "Vampires")

(cons element my_vector)

; Convert vectors, lists, sets and maps to seqs:
(seq my_vector)
(seq my_map)

; Add a sequence to another seq
(into '() (seq ["Empathy" "Decorating"]))

; Note: the order will be reversed here! The following will return false
(= my_list (into '() (seq ["Empathy Decorating"])))

(map str ["a" "b" "c"] ["A" "B" "C"])

(list (str "a" "A") (str "b" "B") (str "c" "C"))

(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])

(defn unify-diet-data
  [human critter]
  {:human   human
   :critter critter})

(map unify-diet-data human-consumption critter-consumption)

; Assigning anonymous functions to a variable is the same as function definition
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))

(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)

(reduce
 (fn [new-map [key val]]
   (assoc new-map key (inc val)))
 {}
 {:max 30 :min 10})


; Comparable to update for dicts in Python
(assoc {:a 1} :b 2)

(reduce
 (fn [new-map [key val]]
   (if (> val 4)
     (assoc new-map key val)
     new-map))
 {}
 {:human   4.1
  :critter 3.9})

; This implements map using reduce, but reverses the order of the elements
; It also forces the output to be a list, instead of the same type as the input seq
(defn map_fn
  [input_fn, input_seq]
  (reduce
   (fn [new-seq element]
     (cons (input_fn element) new-seq))
   '()
   input_seq))

; This implements map using reduce, and *still* reverses the order of the elements
; That is because conj works different for vectors and lists
;[2 3 4]
;clojure-demo.core=> (conj [1 2 3] 4)
;[1 2 3 4]
;clojure-demo.core=> (conj '(1 2 3) 4)
;(4 1 2 3)
(defn map_fn
  [input_fn, input_seq]
  (reduce
   (fn [new-seq element]
     (conj new-seq (input_fn element)))
   '()
   input_seq))

(defn map_fn
  [input_fn, input_seq]
  (reduce
   #(conj %1 (input_fn %2))
   []
   input_seq))

