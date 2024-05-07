(ns gabemeister.utils.slides
  (:require [gabemeister.data.slides :refer [ALL-SLIDE-PARTS]]
            [gabemeister.utils.coll :refer [find-index]]
            [ring.util.codec :as codec]))

;; 
;; PRIVATE
;; 

(defn- is-matching-slide-info
  [slide-info-to-match slide-info]
  (and (= (:slide slide-info-to-match)
          (:slide slide-info))
       (= (:part slide-info-to-match)
          (:part slide-info))))


;; 
;; PUBLIC
;; 

(defn get-slide-query-params
  [slide-str part-str]
  (codec/form-encode {:slide slide-str
                      :part part-str}))

(defn get-next-slide-info
  [slide-str part-str]
  (let [curr-idx (find-index #(is-matching-slide-info {:slide slide-str
                                                       :part part-str}
                                                      %)
                             ALL-SLIDE-PARTS)
        next-idx (+ 1 curr-idx)]
    (get ALL-SLIDE-PARTS next-idx)))

(defn get-prev-slide-info
  [slide-str part-str]
  (let [curr-idx (find-index #(is-matching-slide-info {:slide slide-str
                                                       :part part-str}
                                                      %)
                             ALL-SLIDE-PARTS)
        prev-idx (- curr-idx 1)]
    (get ALL-SLIDE-PARTS prev-idx)))

(defn get-next-slide-query-params
  [slide-str part-str]
  (let [next-slide-info (get-next-slide-info slide-str part-str)]
    (if (nil? next-slide-info)
      nil
      (get-slide-query-params (:slide next-slide-info)
                              (:part next-slide-info)))))

(defn get-prev-slide-query-params
  [slide-str part-str]
  (let [prev-slide-info (get-prev-slide-info slide-str part-str)]
    (if (nil? prev-slide-info)
      nil
      (get-slide-query-params (:slide prev-slide-info)
                              (:part prev-slide-info)))))
