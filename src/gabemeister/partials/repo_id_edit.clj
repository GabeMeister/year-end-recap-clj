(ns gabemeister.partials.repo-id-edit
  (:require [gabemeister.utils.templates :refer [templ]]
            [gabemeister.db.repos :refer [get-repo-by-id]]
            [gabemeister.components.loading-spinner :refer [loading-spinner]]
            [gabemeister.components.text-input :refer [text-input]]
            [gabemeister.components.button :refer [button]]))

(defn repo-id-edit
  [id]
  (let [repo-id (Integer/parseInt id)
        repo (get-repo-by-id repo-id)
        cancel-path (str "/repo/" repo-id)
        put-path (str "/repo/" id)
        name (:repos/name repo)
        loading-id (str "edit-loading-" repo-id)]
    (templ
     [:form
      {:hx-put put-path
       :hx-target "this"
       :hx-swap "outerHTML"}
      [:div
       [:label {:for "repoName"} "Repo Name"]
       [:br]
       [:div
        (text-input {:id "repoName"
                     :name "repoName"
                     :value name
                     :class "w-96"})]]
      [:div
       {:class "mt-2 flex items-center"}
       (button {} "Save")
       (button {:hx-get cancel-path
                :hx-indicator (str "#" loading-id)
                :color "gray"
                :class "ml-2"}
               "Cancel")
       (loading-spinner {:id loading-id :class "htmx-indicator ml-2"})]])))