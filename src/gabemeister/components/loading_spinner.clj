(ns gabemeister.components.loading-spinner
  (:require [gabemeister.utils.twm :refer [twm]]))


(defn loading-spinner
  ;; Pass in :id and :class as props. Used with hx-indicator
  ([] (loading-spinner {:id "spinner"}))
  ([props]
   (let [class-str (twm "animate-spin w-3" (:class props))]
     [:img
      {:id (:id props)
       :class class-str
       :src "/img/loading-spinner.svg"
       :alt "loading-spinner"}])))
