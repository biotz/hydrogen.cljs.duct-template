;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

(ns {{namespace}}.client.main
  (:require [ajax.core :as ajax]
    [day8.re-frame.http-fx]

    [re-frame.core :as re-frame]
    [reagent.core :as reagent]

    [{{namespace}}.client.view :as view]
    ;;[{{namespace}}.routes :as routes]
    ;;[{{namespace}}.home :as home]
    ;;[{{namespace}}.encryption-toy :as encryption-toy]
    [{{namespace}}.client.todo :as todo]
    ;;[{{namespace}}.landing :as landing]
    ))

(re-frame/reg-sub
  ::active-view
  (fn [db _]
      (get db :active-view)))

(re-frame/reg-event-db
  ::set-config
  (fn [db [_ {:keys [config]}]]
      (assoc db :oidc-config config)))

(re-frame/reg-event-db
  ::error
  (fn [db [_ _]]
      (assoc db :error :unable-to-load-config)))

#_(re-frame/reg-event-fx
  ::get-config
  (fn [{:keys [db]} [_]]
      {:http-xhrio {:method :get
                    :uri "/oidc-config"
                    :format (ajax/json-request-format)
                    :response-format (ajax/transit-response-format)
                    :on-success [::set-config]
                    :on-failure [::error]}}))

(defn main []
      (let [active-view (re-frame/subscribe [::view/active-view])]
           (fn []
               (case @active-view
                     ;;:landing [landing/main]
                     :home [home/main]
                     :todo-list [todo/main]
                     ;;:encryption-toy [encryption-toy/main]
                     ))))

(defn dev-setup []
      (when goog.DEBUG
            (enable-console-print!)
            (println "Dev mode")))

(defn mount-root []
      (re-frame/clear-subscription-cache!)
      (reagent/render [:div.app-container
                       (js/console.log (range 20))
                       (js/console.log {:recipe-tile "Mac and Cheese"
                                        :ingredients [{:name "Pasta"
                                                       :category ""}
                                                      {:name "Cheese"
                                                       :category "Dairy"
                                                       :recommended-type "Cheddar"}]})

                       [main]]
                      (.getElementById js/document "app")))

(defn ^:export init []
      (dev-setup)
      (re-frame/dispatch-sync [::get-config])
      (routes/app-routes)
      (mount-root))

