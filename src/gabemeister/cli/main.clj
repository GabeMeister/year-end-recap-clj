(ns gabemeister.cli.main
  (:require [clojure.java.shell :refer [sh]]))

;; You can run this on the cli with:
;;   `clj -M -m gabemeister.cli.main`

;;
;; CONSTS
;;

(def repos [{:name "RedBalloon Frontend",
             :url "https://gitlab.redballoon.dev/frontend/rb-frontend",
             :path "/home/gabe/dev/rb-frontend",
             :ssh-clone-url "https://gitlab.redballoon.dev/frontend/rb-frontend.git",
             :host "gitlab",
             :duplicate-authors {"Calvin Freitas" "Cal Freitas",
                                 "Isaac" "Isaac Neace",
                                 "Kenny Kline" "Kenny",
                                 "EzraYoungren" "Ezra Youngren",
                                 "Stephen Bremer" "Steve Bremer"},
             :include-files ["ts", "tsx", "js", "jsx"],
             :exclude-dirs ["node_modules", ".next"],
             :master-branch "master",
             :master-merge-snippet "%into ''master''%",
             :test-functions ["it(", "test("]}])

(def YEAR 2023)

;;
;; FUNCTIONS
;;


(sh "mergestat"
    "SELECT author_name as name, count(*) as commits
     FROM commits
     WHERE author_when >= DATETIME('2023-01-01 00:00:00')
       AND author_when <= DATETIME('2023-12-31 23:59:59')
     GROUP BY name
     HAVING commits > 1
     ORDER BY commits desc
     LIMIT 2;"
    "-f"
    "json")

(defn get-commits-by-author
  [repo]

  (println "In get-commits-by-author"))

(defn -main
  []
  (doseq [repo repos]
    (println (str "Processing " (:name repo)))
    (get-commits-by-author repo)))

(-main)

