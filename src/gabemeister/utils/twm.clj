(ns gabemeister.utils.twm
  (:require [clojure.string :as str]
            [clojure.pprint :refer [pprint]]
            [gabemeister.utils.map :refer [remove-nested]]
            [gabemeister.data.twm-consts :refer [CLASS-MAP
                                                 CONFLICTING-CLASSES
                                                 CUSTOM-CLASS-PROPERTIES]]))

;;
;; GLOSSARY
;;

;; - class-str: the full string of a class name (e.g. `hover:md:mt-4`)

;; - class-property-str: the class name without the modifiers (e.g. `mt-4`)

;; - class-group-str: the "group" that the class is a part of (e.g. "display" is
;;        the class group for the `flex` css class)

;; - modifier: the prefix(es) before a class property (e.g. the `md:` on `mt-4`)


;;
;; EXAMPLES
;;

;; Given the following css string:

;; "gabe-special-class inline-flex text-green-700 hover:text-yellow-500
;; md:hover:text-red-500"

;; The corresponding map is built below. Notice the root level keys are class
;; groups, modifiers are nested keys and finally the values are the css classes
;; themselves. Any classes that aren't ready to be handled are tossed into the
;; "unrecognized-classes" vector.

;; {"display" {"value" "inline-flex"}
;;  "text-color" {"value" "text-green-700"
;;                "hover" {"value" "text-yellow-500"
;;                         "md" "text-red-500"}}
;;  "unrecognized" ["gabe-special-class"]}


;;
;; FUNCTIONS
;;

(defn- recursive-get-css
  ;; Given a map of css and the current css string, iterate through all the
  ;; keys/values of the map and form a string containing all of the css classes
  [css-map css-str]
  (reduce (fn [final-css-str [key, val]] (if (= key "value")
                                           (str/trim (str final-css-str " " val))
                                           (str/trim (str final-css-str " " (recursive-get-css val css-str)))))
          css-str
          css-map))

(defn- css-class-group-map-to-str
  ;; Given a class group name and the corresponding css map that lives under
  ;; that name, convert the css map into a string. If the class group is
  ;; `unrecognized`, it is special and is just a straight vector of unrecognized
  ;; class names.
  [[class-group-str css-map]]
  (if (= "unrecognized" class-group-str)
    (str/join " " css-map)
    (recursive-get-css css-map "")))

(defn- css-map-to-str
  ;; Given a css map, return the final css class string
  [css-map]
  (str/trim (reduce (fn [acc css-str] (str acc " " (str/trim css-str)))
                    ""
                    (map css-class-group-map-to-str css-map))))

(defn- parse-modifiers
  ;; Given a string css class (e.g. `md:hover:text-blue-100`), return a list of
  ;; the modifiers in alphabetical order ( e.g. '('hover', 'md') )
  [class]
  (if (str/includes? class ":")
    (let [modifiers (butlast (sort (str/split class #":")))]
      modifiers)
    '()))

(defn- is-standard-class?
  [class-property-str]
  (contains? CLASS-MAP class-property-str))

(defn- is-custom-class?
  ;; Is the class a class with a custom space (e.g. rounded-[3px])
  [class-property-str]
  (boolean (some (fn [val] (str/starts-with? class-property-str
                                             val))
                 CUSTOM-CLASS-PROPERTIES)))

(defn- is-recognized-class?
  ;; Check whether a given a specific css class needs to be processed via twm. 
  [class-property-str]
  (or (is-standard-class? class-property-str)
      (is-custom-class? class-property-str)))

(defn- parse-css-property
  ;; Given a full css class, return just the css property itself without modifiers
  ;; (e.g. remove `md:hover:` in front) and without spacing (e.g. `-[20px]` or
  ;; `-24` at the end)
  [class-str]
  ;; Example classes: `md:hover:w-24`, `flex`, `table-cell`
  (let [class-without-modifiers-str (last (str/split class-str #":"))]
    class-without-modifiers-str))

(defn- remove-conflicting-css-classes
  ;; Given a css map that already has values, remove any css classes within a
  ;; class group that has conflicts with a new class that was just recently
  ;; inserted
  [css-map css-path-vec class-group-str]
  (if (contains? CONFLICTING-CLASSES class-group-str)
    (let [conflicting-class-groups-vec (get CONFLICTING-CLASSES class-group-str)]
      (loop [class-groups-vec conflicting-class-groups-vec
             css-map css-map]
        (if (= 0 (count class-groups-vec))
          css-map
          (let [class-group-str (first class-groups-vec)
                css-path-to-remove-vec (assoc css-path-vec 0 class-group-str)]
            (recur (rest class-groups-vec)
                   (remove-nested css-map css-path-to-remove-vec))))))
    css-map))

(defn- add-class-to-css-map
  ;; Given a css map, add in another class into the map and deal with conflicts
  [css-map css-path-list class-str class-group-str]
  (let [css-map-with-new-class (assoc-in css-map
                                         css-path-list
                                         class-str)
        css-map-without-conflicts (remove-conflicting-css-classes css-map-with-new-class
                                                                  css-path-list
                                                                  class-group-str)]
    css-map-without-conflicts))

(defn- get-custom-css-class-group
  [css-property-str]
  (let [css-group-str (first (str/split css-property-str #"-"))]
    css-group-str))

(defn- get-class-group-from-css-property
  [css-property-str]
  (if (is-standard-class? css-property-str)
    (get CLASS-MAP css-property-str)
    (get-custom-css-class-group css-property-str)))

(defn- css-coll-to-map-reducer
  ;; The meat of the twm function. Convert a collection of random css properties
  ;; into a css map.
  [css-map class-str]
  (let [css-property (parse-css-property class-str)]
    (if (is-recognized-class? css-property)
      (let [modifiers-list (parse-modifiers class-str)
            class-group-str (get-class-group-from-css-property css-property)
            css-path-list (conj (vec (conj modifiers-list class-group-str)) "value")
            new-css-map (add-class-to-css-map css-map css-path-list class-str class-group-str)]
        new-css-map)
      (assoc-in css-map ["unrecognized"] (conj (get css-map "unrecognized" []) class-str)))))

(defn- css-coll-to-map
  ;; Given a collection of css classes, return a map with root-level keys as css
  ;; class properties (e.g. `pt` for padding top), nested keys as modifiers
  ;; (e.g. `md` or `hover`), and the values as the full css classes themselves
  ;; (e.g. `hover:pt-4`)
  ([css-coll] (css-coll-to-map css-coll {}))
  ([css-coll init-map]
   (reduce css-coll-to-map-reducer init-map css-coll)))

(defn- css-str-to-list
  ;; Given a string of css classes, return a list of each individual class
  [css-str]
  (sort (str/split css-str #" ")))

(defn- css-to-map
  ;; Given a css string of several css classes, return a map representing the
  ;; css structure
  ([css-str] (css-to-map css-str {}))
  ([css-str init-map]
   (let [css-list (css-str-to-list css-str)
         css-map (css-coll-to-map css-list init-map)]
     css-map)))

(defn twm
  ;; Override default tailwind classes with new tailwind classes
  [orig-css override-css]
  (if (or (nil? override-css)
          (= "" override-css))
    orig-css
    (let [orig-css-map (css-to-map orig-css)
          override-css-map (css-to-map override-css orig-css-map)
          new-css (css-map-to-str override-css-map)]
      new-css)))
