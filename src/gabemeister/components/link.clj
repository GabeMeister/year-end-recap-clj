(ns gabemeister.components.link
  (:require [gabemeister.utils.twm :refer [twm]]))

(defn link
  ([props]
   (link props nil))
  ([props content]
   (let [class-str (:class props)
         props (dissoc props :class)]
     [:a
      (merge {:class (twm "cursor-pointer
                           text-blue-500
                           hover:text-blue-700
                           transition
                           duration-500"
                          class-str)}
             props)
      content])))