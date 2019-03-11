;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.client.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [goog.events]
            [goog.history.EventType :as EventType]
            [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [<<namespace>>.client.view :as view]
            [<<namespace>>.client.home :as home]
            [<<namespace>>.client.todo :as todo]))

(defn hook-browser-navigation! []
      (doto (History.)
            (goog.events/listen
              EventType/NAVIGATE
              (fn [event]
                  (secretary/dispatch! (.-token event))))
            (.setEnabled true)))

(defn app-routes []
      (secretary/set-config! :prefix "#")
      ;; --------------------
      ;; define routes here

      (defroute "/" []
                (view/redirect! "/#/home"))

      (defroute "/home" []
                (re-frame/dispatch [::home/go-to-home]))

      (defroute "/todo-list" []
                (re-frame/dispatch [::todo/go-to-todo]))

      ;; --------------------
      (hook-browser-navigation!))