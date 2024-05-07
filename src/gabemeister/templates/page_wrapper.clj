(ns gabemeister.templates.page-wrapper)


(defn page-wrapper
  [content]
  [:html
   [:head
    [:script {:src "https://unpkg.com/htmx.org@1.9.10/dist/htmx.min.js"}]
    [:link {:rel "icon"
            :type "image/x-icon"
            :href "/img/favicon.ico"}]
    [:link {:rel "stylesheet"
            :href "/css/styles.css"}]
    [:title "Year End Recap"]

    [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
    [:link {:rel "preconnect" :href "https://fonts.gstatic.com"}]
    [:link {:href "https://fonts.googleapis.com/css2?family=Lato&display=swap"
            :rel "stylesheet"}]
    [:link {:href "https://fonts.googleapis.com/css2?family=Bitter&family=Lato&display=swap"
            :rel "stylesheet"}]]
   [:body
    content]])