(ns gabemeister.data.slides)

(def ALL-SLIDES [:about

                 :new_authors
                 :team_commits
                 :file_count
                 :lines_of_code
                 :longest_files

                 :author_commits_over_time
                 :team_commits_by_month
                 :team_commits_by_week_day
                 :team_commits_by_hour
                 :highest_commit_day_by_author

                 :longest_commit
                 :shortest_commits
                 :commit_message_lengths

                 :author_blame_counts

                 :avg_releases_per_day
                 :most_releases_in_day
                 :ending])

(def SLIDE-PARTS
  {:about ["main"]
   :new_authors
   ["title" "prev_year_number" "curr_year_number" "list_names"],
   :team_commits
   ["title" "prev_year_number" "curr_year_number"],
   :file_count
   ["title" "prev_year_number" "curr_year_number"],
   :lines_of_code
   ["title" "prev_year_number" "curr_year_number"],
   :longest_files
   ["title" "third_place" "second_place" "first_place"],
   :author_commits_over_time ["title" "main"],
   :team_commits_by_month ["title" "main"],
   :team_commits_by_week_day ["title" "main"],
   :team_commits_by_hour ["title" "main"],
   :highest_commit_day_by_author ["title" "author" "commits"],
   :longest_commit ["title" "author" "commit"],
   :shortest_commits ["title" "first" "second" "third" "fourth" "fifth"],
   :commit_message_lengths ["main"],
   :author_blame_counts ["title" "main"],
   :avg_releases_per_day ["title" "main"],
   :most_releases_in_day ["title" "main"],
   :ending ["main"]})

(defn- get-slide-parts
  ;; Returns a vector of {:slide :part} maps
  [slide-kw]
  (if (contains? SLIDE-PARTS slide-kw)
    (let [slide-parts (slide-kw SLIDE-PARTS)]
      (vec (map (fn
                  [part-str]
                  {:slide (name slide-kw)
                   :part part-str})
                slide-parts)))
    [{:slide (name slide-kw)
      :part "main"}]))

(defn- slide-part-reducer
  [all-slide-parts-vec curr-slide-kw]
  (let [slide-parts-vec (get-slide-parts curr-slide-kw)]
    (vec (concat all-slide-parts-vec slide-parts-vec))))

(def ALL-SLIDE-PARTS (reduce slide-part-reducer [] ALL-SLIDES))