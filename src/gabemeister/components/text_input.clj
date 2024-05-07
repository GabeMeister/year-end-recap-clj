(ns gabemeister.components.text-input
  (:require [clojure.spec.alpha :as s]
            [gabemeister.utils.twm :refer [twm]]))

(defn text-input
  ;; Props:
  ;;  - :id - string
  ;;  - :name - string
  ;;  - :value - string
  ;;  - :class - string
  [props]
  (let [id (:id props)
        name (:name props)
        value (:value props)
        class-str (twm "py-1 px-2 outline-none animate-color duration-300 border-[1px] border-gray-300 focus:border-blue-300 rounded-[3px]"
                       (:class props))]
    [:input
     {:type "text"
      :id id
      :class class-str
      :name name
      :value value}]))
