(ns gabemeister.utils.url
  (:require [ring.util.codec :as codec]))

(defn get-url-with-query-params
  [base-url query-params-map]
  (str base-url "?" (codec/form-encode query-params-map)))
