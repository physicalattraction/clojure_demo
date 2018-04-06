;19 Last Element
;Special restriction: last

;Remove all elements until only one is left
(fn my-last
  [my-seq]
  (if (empty? (rest my-seq))
    (first my-seq)
    (recur (rest my-seq))))

;Reverse the sequence and take the first
(comp first reverse)

;20 Penultimate Element

;Reverse the sequence and take the second
(comp second reverse)

;21 Nth Element
;Special restriction: nth

;Remove all elements until the nth element
(fn my-nth
  [my-seq n]
  (if (= n 0)
    (first my-seq)
    (recur (rest my-seq) (dec n))))

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

;Use apply to convert the input sequence to a list of arguments
(fn my-sum
  [input]
  (apply + input))

;AN even shorter way is just using `apply +` as answer

;Use reduce to add all elements into a new sum
(fn my-sum
  [input]
  (reduce
   (fn [new-sum element] (+ new-sum element))
   0
   input))

;Do the same, making use of default values in reduce
(fn my-sum
  [input]
  (reduce + input))

;An even shorter way is just using `reduce +` as answer

;25 Find the odd numbers

;Use filter and a descriptor
(fn find-odd
  [input]
  (filter #(= (mod % 2) 1) input))

;An even shorter way is just `filter #(= (mod % 2) 1)` as answer

;Use reduce and a custom filter
(fn find-odd
  [input]
  (reduce
   (fn [new-seq element] (if (= (mod element 2) 1) (conj new-seq element) new-seq))
   []
   input))

;26 Fibonacci Sequence

;Recursively add Fibonacci numbers to the sequence
;Note that take-last is safe against taking too many elements: (take-last 3 [1 2]) evaluates to (1 2)
(fn fibonacci
  ([seq-length]
   (fibonacci seq-length [1]))
  ([seq-length seq-so-far]
   (if (= (count seq-so-far) seq-length)
     seq-so-far
     (recur seq-length (conj seq-so-far (apply + (take-last 2 seq-so-far)))))))
