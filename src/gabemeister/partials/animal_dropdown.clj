(ns gabemeister.partials.animal-dropdown
  (:require [gabemeister.utils.templates :refer [templ]]
            [gabemeister.components.loading-spinner :refer [loading-spinner]]))

(defn animal-dropdown
  [animal]
  (let [caps? (= animal "zebra")]
    (templ [:div {:class "animal-dropdown-wrapper"}
            [:div {:class "flex "}
             [:select {:name "animal"
                       :hx-post "/animal-dropdown"
                       :hx-target "closest .animal-dropdown-wrapper"
                       :hx-swap "outerHTML"
                       :hx-indicator "#animal-dropdown-loading"}
              [:option {:value "dog"
                        :selected (if (= animal "dog") "selected" nil)}
               (if caps? "DOG" "Dog")]
              [:option {:value "cat"
                        :selected (if (= animal "cat") "selected" nil)}
               (if caps? "CAT" "Cat")]
              [:option {:value "zebra"
                        :selected (if (= animal "zebra") "selected" nil)}
               (if caps? "ZEBRA" "Zebra")]]
             (loading-spinner {:id "animal-dropdown-loading"
                               :class "htmx-indicator ml-1"})]])))
