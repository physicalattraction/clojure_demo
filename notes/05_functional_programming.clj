(defn sum
  ([vals]
   (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (sum (rest vals) (+ (first vals) accumulating-total)))))

; Use recur for performance reasons
(defn sum
  ([vals]
   (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (recur (rest vals) (+ (first vals) accumulating-total)))))

(require
 '[clojure.string :as s])

(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))

(clean "My boa constrictor is so sassy lol!  ")

;Compose functions from other functions
;comp(f1 f2 f3)(x) = f1(f2(f3(x)))
((comp inc *) 2 3)

(def character
  {:name       "Smooches McCutes"
   :attributes {:intelligence 10
                :strength     4
                :dexterity    5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

; The following two function definitions are the same
(defn spell-slots
  [character]
  (int (inc (/ (c-int character) 2))))

(def spell-slots-comp (comp int inc #(/ % 2) c-int))

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

(defn my-comp
  ([f]
   f)
  ([f & fs]
   (fn [& args] (f (apply (apply my-comp fs) args)))))

((my-comp inc) 3)
((my-comp inc #(+ % 3)) 3)
((my-comp inc #(+ % 3) *) 2 3)

(defn sleepy-identity
  "Returns the given value after 1 second"
  [x]
  (Thread/sleep 1000)
  x)
(def memo-sleepy-identity (memoize sleepy-identity))
(memo-sleepy-identity "Mr. Fantastico")
(memo-sleepy-identity "Mr. Fantastico")
