;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

(ns {{namespace}}.client.home
  (:require
    ;;[hyd.client.session :as session]
    ;;[{{namespace}}.client.view :as view]
    ;;[{{namespace}}.client.landing :as landing]
    [re-frame.core :as re-frame]))

(defn logout []
      [:div.logout
       {:on-click #(js/alert "not ready!")}
       "Logout"])

(defn links []
      [:div {:id "home-links"}
       [:a {:href "/#/todo-list"} "TODO LIST"]
       [:a {:href "/#/encryption-toy"} "ENCRYPTION"]])

(defn main []
      [:div {:id "home"}
       [:img {:src "assets/hydrogen-logo-white.svg" :alt "Hydrogen logo"}]
       [:h1 "Welcome to Hydrogen!"]
       [:p "What do you want to play with?"]
       [links]
       [logout]])
