(ns gabemeister.utils.coll
  (:require [clojure.string :as str]))

(defn find-item
  [pred? init-coll]
  (loop [coll init-coll]
    (let [item (first coll)]
      (if (pred? item)
        item
        (recur (rest coll))))))

(defn find-index
  [pred? coll]
  (first (keep-indexed #(when (pred? %2) %1) coll)))
