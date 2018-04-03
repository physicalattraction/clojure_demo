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

(map #(titleize (second %)) my_map)

;Create an anonymous function from another function

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

;take returns the first n elements of the sequence, whereas drop returns the sequence with the first n elements removed
(take 3 [1 2 3 4 5 6 7 8 9 10])

(drop 3 [1 2 3 4 5 6 7 8 9 10])

;take-while and drop-while each take a predicate function (a function whose return value is evaluated for truth or
;falsity) to determine when it should stop taking or dropping.
(def ten [1 2 3 4 5 6 7 8 9 10])

;Take all numbers smaller than 7, then drop all numbers smaller than 4
(drop-while #(< % 4) (take-while #(< % 7) ten))

;filter returns all elements of a sequence that test true for a predicate function
(filter #(= (mod % 3) 0) ten)
(filter #(= (rem % 3) 0) ten)

;some returns the first truthy value (any value that’s not false or nil) returned by a predicate function
(some #(= (mod % 3) 0) ten)
(some #(= (mod % 11) 0) ten)

;Here, a slightly different anonymous function uses and to first check whether the condition is true,
;and then returns the entry when the condition is indeed true.
(some #(and (= (mod % 3) 0) %) ten)

;You can sort elements in ascending order with sort
(sort [4 2 3 1])

;You can sort by a given sort descriptor using sort-by
(sort-by #(mod % 3) ten)

;You can use this to reverse ordering
(sort-by - ten)

;concat appends the members of one sequence to the end of another
(concat [1 2] [3 4])
(concat ten (map #(+ % 10) ten))

;Example of lazy evaluation
(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true :name "McMackson"}
   2 {:makes-blood-puns? true, :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true, :has-pulse? true :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first
   (filter vampire?
           (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 0))
(time (identify-vampire (range 0 1000000)))

;repeat generates an infinite sequence with the given element
;repeatedly generates an infinite sequence by applying the given function every time
(concat (take 8 (repeat "na")) ["Batman!"])

(take 3 (repeatedly (fn [] (rand-int 10))))
(take 3 (repeatedly #(rand-int 10)))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 10 (even-numbers))

;This will create an infinite loop
;(take-while #(= (mod % 2) 0) (even-numbers))

(into {:favorite-emotion "gloomy"}
      [[:sunlight-reaction "Glitter!"] [:favorite-color "blood red"]])

;apply explodes a seqable data structure so it can be passed to a function that expects a rest parameter
;similar to *args in Python
(apply max [0 1 2])

;partial = currying
(def add10 (partial + 10))

(add10 3 5)

;This is how partial could be implemented
(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn      (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))
(def error (partial lousy-logger :emergency))

(defn identify-humans
  [social-security-numbers]
  (filter (complement vampire?)
          (map vampire-related-details social-security-numbers)))

;This is how complement could be implemented
(defn my-complement
  [fun]
  (fn [& args]
    (not (apply fun args))))
