;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.client.view
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::active-view
 (fn [db]
   (get db :active-view <<#hydrogen-cljs-session?>>:landing<</hydrogen-cljs-session?>><<^hydrogen-cljs-session?>>:home<</hydrogen-cljs-session?>>)))

(re-frame/reg-event-db
 ::set-active-view
 (fn [db [_ active-view]]
   (assoc db :active-view active-view)))

(defn redirect! [loc]
  (set! (.-location js/window) loc))

(re-frame/reg-fx
 :redirect
 (fn [loc]
   (redirect! loc)))
