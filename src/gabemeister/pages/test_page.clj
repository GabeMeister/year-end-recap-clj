(ns gabemeister.pages.test-page
  (:require [gabemeister.utils.templates :refer [templ]]
            [gabemeister.templates.page-wrapper :refer [page-wrapper]]))

(defn test-page
  []
  (templ (page-wrapper [:div "This is the test page!!"])))

