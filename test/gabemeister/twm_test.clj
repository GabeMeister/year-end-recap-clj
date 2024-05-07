(ns gabemeister.twm-test
  (:require [clojure.test :refer :all]
            [gabemeister.utils.twm :refer [twm]]
            [clojure.string :as str]))

(defn identical-classes?
  ;; Check that two strings contain the same classes, even if the class names
  ;; are out of order
  [c1 c2]
  (let [ordered-c1 (sort (str/split c1 #" "))
        ordered-c2 (sort (str/split c2 #" "))]
    (= ordered-c1 ordered-c2)))

(deftest test-twm
  (testing "tailwind merge"
    (is (identical-classes? (twm "m-1" "m-2") "m-2"))
    (is (identical-classes? (twm "md:mt-1" "mt-2") "md:mt-1 mt-2"))
    (is (identical-classes? (twm "mt-1 md:mt-4" "mt-2") "md:mt-4 mt-2"))
    (is (identical-classes? (twm "mt-1 md:mt-4" "mt-2 md:mt-6") "mt-2 md:mt-6"))
    (is (identical-classes? (twm "mt-1 md:mt-4" "mt-2 md:mt-6 lg:mt-10") "mt-2 md:mt-6 lg:mt-10"))
    (is (identical-classes? (twm "mt-1" "mt-2")  "mt-2"))
    (is (identical-classes? (twm "md:mt-1" "md:mt-2") "md:mt-2"))
    (is (identical-classes? (twm "md:mt-1 random-class-gabe" "md:mt-2") "random-class-gabe md:mt-2"))
    (is (identical-classes? (twm "mt-1" "m-4") "m-4"))
    (is (identical-classes? (twm "m-1" "mb-4") "m-1 mb-4"))
    (is (identical-classes? (twm "mt-1" "mb-4") "mt-1 mb-4"))
    (is (identical-classes? (twm "text-blue" "text-sm") "text-blue text-sm"))
    (is (identical-classes? (twm "mt-[10px]" "mt-[20px]") "mt-[20px]"))
    (is (identical-classes? (twm "mt-4" "mt-[4px]") "mt-[4px]"))
    (is (identical-classes? (twm "m-10" "mt-[20px]") "m-10 mt-[20px]"))
    (is (identical-classes? (twm "m-10 mt-[10px]" "mt-[20px]") "m-10 mt-[20px]"))
    (is (identical-classes? (twm "w-3" "w-6 h-6") "w-6 h-6"))
    nil))
