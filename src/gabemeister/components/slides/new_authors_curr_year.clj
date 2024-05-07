(ns gabemeister.components.slides.new-authors-curr-year
  (:require [gabemeister.utils.templates :refer [templ]]
            [gabemeister.db.repos :refer [get-repo-by-id]]
            [gabemeister.templates.page-wrapper :refer [page-wrapper]]
            [gabemeister.utils.slides :refer [get-next-slide-query-params
                                              get-prev-slide-query-params]]
            [gabemeister.components.link :refer [link]]
            [gabemeister.components.loading-spinner :refer [loading-spinner]]))

(defn new-authors-curr-year-slide
  [id-num]
  (let [repo-map (get-repo-by-id id-num)
        prev-slide-url-str (str "/presentation/" id-num "?" (get-prev-slide-query-params "new_authors" "curr_year_number"))
        next-slide-url-str (str "/presentation/" id-num "?" (get-next-slide-query-params "new_authors" "curr_year_number"))]
    (templ
     (page-wrapper [:div {:class "flex flex-col items-center p-6"}
                    [:div "This is the new authors CURRENT YEAR slide for " (:repos/name repo-map)]

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