;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.client
  (:require [reagent.core :as reagent]
            [<<namespace>>.client.home :as home]))

(defn main []
      [home/main])

(defn dev-setup []
      (when goog.DEBUG
            (enable-console-print!)
            (println "Dev mode")))

(defn mount-root []
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
      (mount-root))
