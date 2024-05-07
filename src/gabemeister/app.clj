(ns gabemeister.app
  (:require [clojure.string :as str]
            [org.httpkit.server :as server]
            [ring.middleware.defaults :refer [wrap-defaults
                                              site-defaults]]
            [ring.middleware.file :refer [wrap-file]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.pprint :refer [pprint]]
            [gabemeister.pages.home-page :refer [home-page]]
            [gabemeister.pages.presentation-page :refer [presentation-page]]
            [gabemeister.pages.test-page :refer [test-page]]
            [gabemeister.partials.repo-id-edit :refer [repo-id-edit]]
            [gabemeister.partials.repo-id :refer [get-repo-id put-repo-id]])
  (:gen-class))

(def port (or (some-> (System/getenv "PORT")
                      parse-long)
              8080))

(defroutes app-routes
  ;; 
  ;; PAGES
  ;;

  (GET "/" [] (home-page))
  (GET "/test" [] (test-page))
  (GET "/presentation/:id"
    [id slide part]
    (presentation-page id slide part))

  ;; 
  ;; PARTIALS
  ;;

  (GET "/repo/:id" [id] (get-repo-id id))
  (PUT "/repo/:id" req (put-repo-id req))
  (GET "/repo/:id/edit" [id] (repo-id-edit id))

  ;;
  ;; MISC
  ;;

  (route/not-found "Not Found!!"))

(defn get-site-defaults
  []
  (let [temp1 (assoc-in site-defaults [:security :anti-forgery] false)
        temp2 (assoc-in temp1 [:static :files] "public")]
    temp2))


(defn -main [& _args]
  (server/run-server
   (wrap-defaults #'app-routes (get-site-defaults))
   {:port port})
  (println "Site running on" (str "http://localhost:" port)))

;; (-main)