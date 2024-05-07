(ns gabemeister.partials.animal-sound)

(defn animal-sound
  [animal-str]
  (case animal-str
    "dog" "Bark bark!"
    "cat" "Meowwww"
    "zebra" "Eheeheheehe"))