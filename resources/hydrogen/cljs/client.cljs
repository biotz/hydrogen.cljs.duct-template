;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.client
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [<<namespace>>.client.home :as home]
            [<<namespace>>.client.routes :as routes]
            [<<namespace>>.client.todo :as todo]
            [<<namespace>>.client.view :as view]))

(defn main []
      (let [active-view (re-frame/subscribe [::view/active-view])]
           (fn []
               (case @active-view
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
      (routes/app-routes)
      (mount-root))
