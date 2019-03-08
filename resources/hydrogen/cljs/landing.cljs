;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

(ns {{namespace}}.landing
  (:require [ajax.core :as ajax]
    ;;[hyd.client.session :as session]
            [{{namespace}}.client.view :as view]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(re-frame/reg-sub
  ::auth-error
  (fn [db]
      (:auth-error db)))

(re-frame/reg-event-fx
  ::go-to-landing
  (fn [_ _]
      {:dispatch [::view/set-active-view :landing]}))

(def credentials (reagent/atom {:username "" :password ""}))

(defn swap-input! [event atom field]
      (swap! atom assoc field (.. event -target -value)))

(defn- do-login-if-enter-pressed [event credentials]
       (js/alert "not ready!"))

(defn login-form []
      [:div "landing page"])

(defn header []
      [:header
       [:h1 "Hydrogen"]])

(defn main []
      [:div.landing-container
       [header]
       [login-form]])
