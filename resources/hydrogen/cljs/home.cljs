;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.client.home
  (:require
    [re-frame.core :as re-frame]<<=\\ //=>>\\! <<#hydrogen-cljs-session?>>
    [<<namespace>>.client.landing :as landing]
    [<<namespace>>.client.session :as session]<</hydrogen-cljs-session?>> //\\=<< >>=//
    [<<namespace>>.client.view :as view]))

(re-frame/reg-event-fx
  ::go-to-home
  (fn [_ _]
      {:dispatch [::view/set-active-view :home]
       :redirect "/#/home"}))
<<#hydrogen-cljs-session?>>
(defn logout []
      [:div.logout
       {:on-click #(js/alert "not ready!")}
       "Logout"])<</hydrogen-cljs-session?>>

(defn links []
      [:div {:id "home-links"}
       [:a {:href "/#/todo-list"} "TODO LIST"]])

(defn main []
      [:div {:id "home"}
       [:img {:src "assets/hydrogen-logo-white.svg" :alt "Hydrogen logo"}]
       [:h1 "Welcome to Hydrogen!"]
       [:p "What do you want to play with?"]
       [links]<<#hydrogen-cljs-session?>>
       [logout]<</hydrogen-cljs-session?>>])
