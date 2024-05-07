(ns gabemeister.components.slides.team-commits-title
  (:require [gabemeister.utils.templates :refer [templ]]
            [gabemeister.db.repos :refer [get-repo-by-id]]
            [gabemeister.utils.slides :refer [get-next-slide-query-params
                                              get-prev-slide-query-params]]
            [gabemeister.templates.page-wrapper :refer [page-wrapper]]
            [gabemeister.components.link :refer [link]]
            [gabemeister.components.loading-spinner :refer [loading-spinner]]))

(defn team-commits-title-slide
  [id-num]
  (let [repo-map (get-repo-by-id id-num)
        prev-slide-url-str (str "/presentation/" id-num "?" (get-prev-slide-query-params "team_commits" "title"))
        next-slide-url-str (str "/presentation/" id-num "?" (get-next-slide-query-params "team_commits" "title"))]
    (templ
     (page-wrapper [:div {:class "flex flex-col items-center p-6"}
                    [:div "This is the team commits title slide for " (:repos/name repo-map)]

                    [:div {:class "flex"}
                     [:div {:class "w-12 flex justify-center prev-btn-wrapper"}
                      (link {:hx-get prev-slide-url-str
                             :hx-target "body"
                             :hx-swap "outerHTML"
                             :hx-push-url "true"
                             :hx-indicator "closest .prev-btn-wrapper"
                             :class "original-content cursor-pointer"}
                            "Prev")
                      [:div {:class "h-6 w-6 child-loading-spinner"}
                       (loading-spinner {:class "w-6 h-6 "})]]
                     [:div {:class "w-12 flex justify-center next-btn-wrapper ml-2"}
                      (link {:hx-get next-slide-url-str
                             :hx-target "body"
                             :hx-swap "outerHTML"
                             :hx-push-url "true"
                             :hx-indicator "closest .next-btn-wrapper"
                             :class "original-content cursor-pointer"}
                            "Next")
                      [:div {:class "h-6 w-6 child-loading-spinner"}
                       (loading-spinner {:class "w-6 h-6 "})]]]]))))