(ns koans.14-destructuring
  (:require [koan-engine.core :refer :all]))

(def test-address
  {:street-address "123 Test Lane"
   :city           "Testerville"
   :state          "TX"})

(meditations
  "Destructuring is an arbiter: it breaks up arguments"
  (= ((fn [[a b]] (str b a))
       [:foo :bar]))

  "Whether in function definitions"
  (= (str "First comes love, "
          "then comes marriage, "
          "then comes Clojure with the baby carriage")
     ((fn [[a b c]] (str "First comes " a ", "
                         "then comes " b ", "
                         "then comes " c " with the baby carriage"))
       ["love" "marriage" "Clojure"]))

  "Or in let expressions"
  (= "Rich Hickey aka The Clojurer aka Go Time aka Macro Killah"
     (let [[first-name last-name & aliases]
           (list "Rich" "Hickey" "The Clojurer" "Go Time" "Macro Killah")]
       (->> (cons (str first-name " " last-name) aliases)
            (interpose " aka ")
            (apply str))))

  "You can regain the full argument if you like arguing"
  (= {:original-parts ["Stephen" "Hawking"] :named-parts {:first "Stephen" :last "Hawking"}}
     (let [[first-name last-name :as full-name] ["Stephen" "Hawking"]]
       (hash-map :original-parts full-name :named-parts (hash-map :first first-name :last last-name))))

  "Break up maps by key"
  (= "123 Test Lane, Testerville, TX"
     (let [{street-address :street-address, city :city, state :state} test-address]
       (str street-address ", " city ", " state)))

  "Or more succinctly"
  (= "123 Test Lane, Testerville, TX"
     (let [{:keys [street-address city state]} test-address]
       (->> (list street-address city state)
            (interpose ", ")
            (apply str))))

  "All together now!"
  (= "Test Testerson, 123 Test Lane, Testerville, TX"
     ((fn [[a b] {:keys [street-address city state]}]
        (->> (list (str a " " b) street-address city state)
             (interpose ", ")
             (apply str)))
       ["Test" "Testerson"] test-address)
  ))
