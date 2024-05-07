(ns gabemeister.pages.home-page
  (:require [gabemeister.utils.templates :refer [templ]]
            [gabemeister.templates.page-wrapper :refer [page-wrapper]]
            [gabemeister.db.repos :refer [get-all-repos]]
            [gabemeister.templates.repo-list-item :refer [repo-list-item]]))

(defn home-page
  []
  (let [projects (get-all-repos)]
    (templ
     (page-wrapper [:div
                    {:class "p-6"}
                    [:ul
                     (for [proj projects]
                       (repo-list-item proj))]]))))
