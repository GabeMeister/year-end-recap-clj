(ns gabemeister.components.button
  (:require [clojure.string :as str]
            [gabemeister.utils.twm :refer [twm]]))

(defn- color-to-css
  ([] (color-to-css "blue"))
  ([color]
   (case color
     "blue" "bg-blue-500 hover:bg-blue-700"
     "gray" "bg-gray-400 hover:bg-gray-600"
     "red" "bg-red-500 hover:bg-red-700"
     nil (color-to-css "blue"))))

(defn button
  "The classic button component
   
   Props:
   - :class - override css styling
   - :color - the color the button should be"
  [props content]
  (let [color-str (color-to-css (:color props))
        class-str (-> "text-white px-3 py-1 rounded-[3px] animate-color duration-300"
                      (twm color-str)
                      (twm (:class props)))
        btn-attr (merge props {:class class-str})]
    [:button
     btn-attr
     content]))
