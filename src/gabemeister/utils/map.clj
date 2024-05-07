(ns gabemeister.utils.map)

(dissoc {} "mt")

(defn remove-nested
  [m [k & ks]]
  (if (or (empty? ks)
          (not (contains? m k)))
    (dissoc m k)
    (update-in m [k] remove-nested ks)))
