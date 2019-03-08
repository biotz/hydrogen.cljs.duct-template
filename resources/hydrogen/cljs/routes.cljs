;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

(ns {{namespace}}.client.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [clojure.string :as s]
            [goog.events]
            [goog.history.EventType :as EventType]
            [re-frame.core :as re-frame]
            [secretary.core :as secretary]
    ;;[hyd.client.session :as session]
            [{{namespace}}.client.view :as view]
            [{{namespace}}.client.home :as home]
    ;;[{{namespace}}.client.todo :as todo]
    ;;[{{namespace}}.client.encryption-toy :as encryption-toy]
            [{{namespace}}.client.landing :as landing]))

(defn hook-browser-navigation! []
      (doto (History.)
            (goog.events/listen
              EventType/NAVIGATE
              (fn [event]
                  (secretary/dispatch! (.-token event))))
            (.setEnabled true)))

#_(defn- anyone? [access-config]
       (every? #(true? (val %)) access-config))

#_(defn- only-authenticated? [{:keys [allow-unauthenticated? allow-authenticated?]}]
       (and allow-authenticated? (not allow-unauthenticated?)))

#_(defn- only-unauthenticated? [{:keys [allow-unauthenticated? allow-authenticated?]}]
       (and (not allow-authenticated?) allow-unauthenticated?))

#_(def ^:const access-config-defaults
  {:allow-unauthenticated? false
   :allow-authenticated? true})

#_(def ^:const default-number-retries 5)

#_(def ^:const default-delay-time 50)

#_(defn config-exists? [db]
      (get db :config))

#_(re-frame/reg-event-db
  ::error
  (fn [db _]
      (assoc db :error "request timed out!")))

#_(re-frame/reg-event-fx
  :go-to*
  ;;[(re-frame/inject-cofx ::session/jwt-token)]
  (fn [{:keys [jwt-token]} [_ evt & access-config]]
      (let [access-config (merge access-config-defaults access-config)]
           (cond
             (anyone? access-config) {:dispatch evt}
             (only-unauthenticated? access-config) (if jwt-token {:redirect "/#/home"} {:dispatch evt})
             (only-authenticated? access-config) (if jwt-token {:dispatch evt} {:redirect "/#/landing"})))))

#_(re-frame/reg-event-fx
  :go-to
  (fn [{:keys [db]} [_ evt & {:keys [allow-authenticated? allow-unauthenticated remaining-retries]
                              :or {remaining-retries default-number-retries}
                              :as access-config}]]
      (cond
        (config-exists? db) {:dispatch [:go-to* evt access-config]}
        (> remaining-retries 0) {:dispatch-later
                                 [{:ms default-delay-time
                                   :dispatch [:go-to evt
                                              (assoc access-config :remaining-retries (dec remaining-retries))]}]}
        :else {:dispatch [::error]})))

(defn app-routes []
      (secretary/set-config! :prefix "#")
      ;; --------------------
      ;; define routes here

      (defroute "/" []
                (view/redirect! "/#/landing"))

      (defroute "/landing" []
                (re-frame/dispatch
                  ;;[:go-to [::landing/go-to-landing] :allow-authenticated? false :allow-unauthenticated? true]
                  [::landing/go-to-landing]))

      (defroute "/home" []
                (re-frame/dispatch
                  ;;[:go-to [::home/go-to-home]]
                  [::home/go-to-home]))

      ;; --------------------
      (hook-browser-navigation!))
