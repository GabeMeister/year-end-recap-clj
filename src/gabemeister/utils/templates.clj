(ns gabemeister.utils.templates
  (:require [hiccup2.core :as h]))

(defn templ
  [content]
  (str (h/html content)))