;19 Last Element
;Special restriction: last

;Remove all elements until only one is left
(fn my-last
  [input]
  (if (empty? (rest input))
    (first input)
    (recur (rest input))))

;Reverse the sequence and take the first. An even shorter way is using `(comp first reverse)` as answer
(fn my-last
  [input]
  ((comp first reverse) input))

;20 Penultimate Element

;Reverse the sequence and take the second. An even shorter way is using `(comp second reverse)` as answer
(fn penultimate
  [input]
  ((comp second reverse) input))

;21 Nth Element
;Special restriction: nth

;Remove all elements until the nth element
(fn my-nth
  [input n]
  (if (= n 0)
    (first input)
    (recur (rest input) (dec n))))

;22 Count a sequence
;Special restriction: count

;Recursively call yourself with the tail of the input
(fn my-count
  ([input]
   (my-count input 0))
  ([input count-so-far]
   (if (empty? (rest input))
     (inc count-so-far)
     (recur (rest input) (inc count-so-far)))))

;23 Reverse a sequence
;Special restrictions: reverse, rseq

;Recursively add the next first element of the sequence to the start of a new sequence
(fn my-reverse
  [input]
  (reduce
   (fn [new-seq element] (conj new-seq element))
   '()
   input))

;24 Sum it all up

;Use apply to convert the input sequence to a list of arguments. An even shorter way is just using `apply +` as answer
(fn my-sum
  [input]
  (apply + input))

;Use reduce to add all elements into a new sum
(fn my-sum
  [input]
  (reduce
   (fn [new-sum element] (+ new-sum element))
   0
   input))

;Use reduce to add all elements into a new sum, making use of default values in reduce.
;An even shorter way is just using `reduce +` as answer
(fn my-sum
  [input]
  (reduce + input))

;25 Find the odd numbers

;Use filter and a descriptor. An even shorter way is just `filter #(= (mod % 2) 1)` as answer
(fn find-odd
  [input]
  (filter #(= (mod % 2) 1) input))

;Use reduce and a custom filter
(fn find-odd
  [input]
  (reduce
   (fn [new-seq element] (if (= (mod element 2) 1) (conj new-seq element) new-seq))
   []
   input))

;26 Fibonacci Sequence

;Recursively add Fibonacci numbers to the sequence.
;Note that take-last is safe against taking too many elements: (take-last 3 [1 2]) evaluates to (1 2)
(fn fibonacci
  ([seq-length]
   (fibonacci seq-length [1]))
  ([seq-length seq-so-far]
   (if (= (count seq-so-far) seq-length)
     seq-so-far
     (recur seq-length (conj seq-so-far (apply + (take-last 2 seq-so-far)))))))

;27 Palindrome Detector

;Verify if the reverse input is the same as the input itself.
;Note that we transform both input and reverse input to a seq, to transform "racecar" into '(\r \a \c \e \c \a \r)
(fn palindrome?
  [input]
  (= (seq input) (seq (reverse input))))

;28 Flatten a Sequence

;Add all elements one by one to a new vector. If the element is a collection itself, first flatten the element by
;recursively calling yourself and then add the elements one by one using into.
(fn flatten-seq
  [input]
  (reduce
   (fn [new-seq element]
     (if (coll? element)
       (into new-seq (flatten-seq element))
       (conj new-seq element)))
   []
   input))

;29 Get the Caps

;Convert the string to a sequence, filter out all upper case characters and concatenate them as a string again
(fn get-caps
  [input]
  (apply str (filter #(Character/isUpperCase %) input)))

;Convert the string to a sequence, filter out all upper case characters and concatenate them as a string again
(fn get-caps
  [input]
  (clojure.string/join (filter #(Character/isUpperCase %) (seq input))))

;30 Compress a Sequence

;Add each element to a new vector, provided it is not the same as the previous one
(fn compress-seq
  [input]
  (reduce
   (fn [new-seq element]
     (if (= (last new-seq) element)
       new-seq
       (conj new-seq element)))
   []
   input))

;31 Pack a Sequence

;Append each element to the last vector if it contains the same element, a
;add each element to a new vector if it is another element
(fn pack-seq
  [input]
  (reduce
   (fn [new-seq element]
     (if (= (first (last new-seq)) element)
       (assoc new-seq (dec (count new-seq)) (conj (last new-seq) element))
       (conj new-seq [element])))
   []
   input))

;Use the built-in function partition-by
(fn pack-seq
  [input]
  (partition-by identity input))

;32 Duplicate a Sequence

;Add each element twice to a new vector
(fn duplicate-seq
  [input]
  (reduce
   (fn [new-seq element]
     (into new-seq [element element]))
   []
   input))

;Use the built-in function interleave
(fn duplicate-seq
  [input]
  (interleave input input))
