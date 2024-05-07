(ns gabemeister.utils.env
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn env
  []
  ;; Using env.json locally
  (if (.exists (io/as-file "./env.json"))
    (json/read-str (slurp "./env.json"))
    (System/getenv)))

(defn get-env
  [key]
  (get (env) key))
