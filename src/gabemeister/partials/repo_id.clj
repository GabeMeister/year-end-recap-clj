(ns gabemeister.partials.repo-id
  (:require [clojure.pprint :refer [pprint]]
            [gabemeister.db.repos :refer [get-repo-by-id update-repo-name]]
            [gabemeister.utils.templates :refer [templ]]
            [gabemeister.templates.repo-list-item :refer [repo-list-item]]))

(defn get-repo-id
  [id]
  (let [repo (get-repo-by-id (Integer/parseInt id))]
    (templ
     (repo-list-item repo))))

(defn put-repo-id
  [req]
  (let [repo-name (get-in req [:params :repoName])
        id (get-in req [:params :id])
        _ (update-repo-name (Integer/parseInt id) repo-name)
        repo (get-repo-by-id (Integer/parseInt id))]
    (templ
     (repo-list-item repo))))