(ns gabemeister.pages.presentation-page
  (:require [gabemeister.utils.templates :refer [templ]]
            [gabemeister.templates.page-wrapper :refer [page-wrapper]]
            [gabemeister.db.repos :refer [get-repo-data-part]]
            [gabemeister.components.slides.about-main :refer [about-main-slide]]
            [gabemeister.components.slides.new-authors-title :refer [new-authors-title-slide]]
            [gabemeister.components.slides.new-authors-prev-year :refer [new-authors-prev-year-slide]]
            [gabemeister.components.slides.new-authors-curr-year :refer [new-authors-curr-year-slide]]
            [gabemeister.components.slides.new-authors-names-list :refer [new-authors-names-list-slide]]
            [gabemeister.components.slides.team-commits-title :refer [team-commits-title-slide]]
            [gabemeister.components.slides.team-commits-prev-year :refer [team-commits-prev-year-slide]]
            [gabemeister.components.slides.team-commits-curr-year :refer [team-commits-curr-year-slide]]
            [gabemeister.utils.conversions :refer [str-to-int]]
            [clojure.data.json :as json]))

(defn presentation-page
  [id-str slide-str part-str]
  (let [slide-type-str (str slide-str "|" part-str)
        id-num (str-to-int id-str)]
    (case slide-type-str
      "|" (about-main-slide id-num)
      "about|main" (about-main-slide id-num)
      "new_authors|title" (new-authors-title-slide id-num)
      "new_authors|prev_year_number" (new-authors-prev-year-slide id-num)
      "new_authors|curr_year_number" (new-authors-curr-year-slide id-num)
      "new_authors|list_names" (new-authors-names-list-slide id-num)
      "team_commits|title" (team-commits-title-slide id-num)
      "team_commits|prev_year_number" (team-commits-prev-year-slide id-num)
      "team_commits|curr_year_number" (team-commits-curr-year-slide id-num)
      "Unrecognized slide")))
