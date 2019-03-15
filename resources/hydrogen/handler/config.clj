;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.handler.config
  (:require [clojure.string :as s]
            [compojure.core :refer [GET]]
            [integrant.core :as ig]))

(defmethod ig/init-key :<<namespace>>.handler/config [_ {:keys [iss client-id]}]
  (GET "/config" req
       (fn [req]
         (let [oidc-config {:iss iss
                            :client-id client-id}]
           {:status 200
            :body {:config oidc-config}
            :headers {"content-type" "application/json"}}))))
