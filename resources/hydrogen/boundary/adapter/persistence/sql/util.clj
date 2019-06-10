;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.boundary.adapter.persistence.sql.util
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.string :as str]
            [duct.logger :refer [log]]))

(defn- convert-identifiers-option-fn
  [x]
  (str/lower-case (str/replace x \_ \-)))

(def ^:private convert-identifiers-option
  "Option map specifying how to convert ResultSet column names to keywords.
  It defaults to clojure.str/lower-case, but our keywords include
  hyphens instead of underscores. So we need to convert SQL
  underscores to hyphens in our keyworks."
  {:identifiers convert-identifiers-option-fn})

(defn- convert-entities-option-fn
  [x]
  (str/replace x \- \_))

(def ^:private convert-entities-option
  "Option map specifying how to convert Clojure keywords/string to SQL
  entity names. It defaults to identity, but our keywords include
  hyphens, which are invalid characters in SQL column names. So we
  change them to underscores."
  {:entities convert-entities-option-fn})

(defn elapsed [start]
  (/ (double (- (System/nanoTime) start)) 1000000.0))

(defn sql-query
  [db-spec logger sql-statement]
  (let [start (System/nanoTime)]
    (try
      (let [result (jdbc/query db-spec sql-statement convert-identifiers-option)
            msec (elapsed start)]
        (log logger :info ::sql-query-success {:msec msec
                                               :count (count result)
                                               :sql-statement sql-statement})
        {:success? true :return-values result})
      (catch Exception e
        (let [result {:success? false}
              msec (elapsed start)]
          (log logger :error ::sql-query-error {:msec msec
                                                :ex-message (.getMessage e)
                                                :sql-statement sql-statement})
          result)))))

(defn sql-insert!
  [db-spec logger table cols values]
  (let [start (System/nanoTime)]
    (try
      (let [count (first (jdbc/insert! db-spec table cols values convert-entities-option))
            msec (elapsed start)]
        (log logger :info ::sql-execute!-success {:msec msec
                                                  :count count
                                                  :cols cols
                                                  :values values})
        {:success? true :inserted-values count})
      (catch Exception e
        (let [result {:success? false}
              msec (elapsed start)]
          (log logger :error ::sql-execute!-error {:msec msec
                                                   :ex-message (.getMessage e)
                                                   :cols cols
                                                   :values values})
          result)))))

(defn sql-insert-multi!
  [db-spec logger table cols values]
  (let [start (System/nanoTime)]
    (try
      (let [count (count (jdbc/insert-multi! db-spec table cols values convert-entities-option))
            msec (elapsed start)]
        (log logger :info ::sql-multi-insert!-success {:msec msec
                                                       :count count
                                                       :cols cols
                                                       :values values})
        {:success? true :inserted-values count})
      (catch Exception e
        (let [result {:success? false}
              msec (elapsed start)]
          (log logger :error ::sql-multi-insert!-error {:msec msec
                                                        :ex-message (.getMessage e)
                                                        :cols cols
                                                        :values values})
          result)))))

(defn sql-update!
  [db-spec logger table set-map where-clause]
  (let [start (System/nanoTime)]
    (try
      (let [count (first (jdbc/update! db-spec table set-map where-clause convert-entities-option))
            msec (elapsed start)]
        (log logger :info ::sql-execute!-success {:msec msec
                                                  :count count
                                                  :set-map set-map
                                                  :where-clause where-clause})
        {:success? true :processed-values count})
      (catch Exception e
        (let [result {:success? false}
              msec (elapsed start)]
          (log logger :error ::sql-execute!-error {:msec msec
                                                   :ex-message (.getMessage e)
                                                   :set-map set-map
                                                   :where-clause where-clause})
          result)))))

(defn sql-execute!
  [db-spec logger sql-statement]
  (let [start (System/nanoTime)]
    (try
      (let [count (first (jdbc/execute! db-spec sql-statement))
            msec (elapsed start)]
        (log logger :info ::sql-execute!-success {:msec msec
                                                  :count count
                                                  :sql-statement sql-statement})
        {:success? true :processed-values count})
      (catch Exception e
        (let [result {:success? false}
              msec (elapsed start)]
          (log logger :error ::sql-execute!-error {:msec msec
                                                   :ex-message (.getMessage e)
                                                   :sql-statement sql-statement})
          result)))))
