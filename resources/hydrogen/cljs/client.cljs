;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.client
  (:require [ajax.core :as ajax]
            [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [reagent.core :as reagent]
            [<<namespace>>.client.home :as home]<<#hydrogen-cljs-session?>>
            [<<namespace>>.client.landing :as landing]<</hydrogen-cljs-session?>>
            [<<namespace>>.client.routes :as routes]
            [<<namespace>>.client.todo :as todo]
            [<<namespace>>.client.view :as view]))

(def default-db
  {})<<#hydrogen-cljs-session?>>

(re-frame/reg-event-db
 ::set-config
 (fn [db [_ {:keys [config]}]]
   (assoc db :config config)))

(re-frame/reg-event-db
 ::error
 (fn [db [_ _]]
   (assoc db :error :unable-to-load-config)))<</hydrogen-cljs-session?>>

(re-frame/reg-event-fx
 ::load-app
 (fn [{:keys [db]} [_]]
   {:db default-db<<#hydrogen-cljs-session?>>
    :http-xhrio {:method :get
                 :uri "/api/config"
                 :format (ajax/json-request-format)
                 :response-format (ajax/transit-response-format)
                 :on-success [::set-config]
                 :on-failure [::error]}<</hydrogen-cljs-session?>>}))

(defn main []
  (let [active-view (re-frame/subscribe [::view/active-view])]
    (fn []
      (case @active-view<<#hydrogen-cljs-session?>>
        :landing [landing/main]<</hydrogen-cljs-session?>>
        :home [home/main]
        :todo-list [todo/main]))))

(defn dev-setup []
  (when goog.DEBUG
    (enable-console-print!)
    (println "Dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [:div.app-container [main]]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (dev-setup)
  (re-frame/dispatch-sync [::load-app])
  (routes/app-routes)
  (mount-root))
