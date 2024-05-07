(ns gabemeister.templates.repo-list-item
  (:require [gabemeister.components.link :refer [link]]
            [gabemeister.components.loading-spinner :refer [loading-spinner]]))

(defn repo-list-item
  [repo]
  (let [id (:repos/id repo)
        edit-link (str "/repo/" id "/edit")
        loader-id (str "edit-spinner-" id)
        external-link (str "/presentation/" (:repos/id repo) "?slide=about&part=main")
        repo-name (:repos/name repo)]
    [:div
     {:hx-target "this" :hx-swap "outerHTML"}
     [:h1 {:class "text-3xl leading-10 flex items-center gabe-heading"}
      (link {:href external-link}
            [:span repo-name])
      [:span {:class "text-lg ml-2 edit-loading"}
       (link {:hx-get edit-link
              :hx-indicator "closest .edit-loading"
              :class "original-content text-sm"}
             "Edit")
       [:div {:class "w-10"}
        (loading-spinner {:id loader-id
                          :class "child-loading-spinner"})]]]]))