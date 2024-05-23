(ns gabemeister.pages.home-page
  (:require [gabemeister.utils.templates :refer [templ]]
            [gabemeister.templates.page-wrapper :refer [page-wrapper]]
            [gabemeister.db.repos :refer [get-all-repos]]
            [gabemeister.templates.repo-list-item :refer [repo-list-item]]))

(defn home-page
  []
  (let []
    (templ
     (page-wrapper [:div
                    {:class "p-12 bg-cyan-950 min-h-screen"}
                    [:div
                     {:class "flex flex-col justify-center items-center text-white"}
                     [:h1
                      {:class "text-5xl text-yellow-400"}
                      "Year End Recap"]
                     [:span {:class "mt-6 text-gray-300 italic"} "To install, run the following in your terminal:"]
                     [:span
                      {:class "inline-block mt-6 text-xl"}
                      "curl -fsSL https://yer-app.fly.dev/install | bash"]]]))))
