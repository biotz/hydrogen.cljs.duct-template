;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

(ns hydrogen.cljs.duct-template
    (:require [clojure.java.io :as io]
              [clojure.string]))

(defn- resource [path]
  (io/resource (str "hydrogen/" path)))

(defn- gen-cascading-routes [project-ns routes-refs]
  (as-> routes-refs $
        (map #(format "#ig/ref :%s.%s" project-ns %) $)
        (clojure.string/join "\n   " $)
        (str "\n  [" $ "]")))

(defn core-profile [{:keys [project-ns profiles]}]
  (let [vars (cond-> {:hydrogen-cljs-core? true}
                     (not (get profiles :hydrogen.cljs/session))
                     (assoc :cascading-routes (gen-cascading-routes project-ns ["static/root"
                                                                                "api/example"])))]
    {:vars vars
     :deps '[[cljs-ajax "0.7.5"]
             [day8.re-frame/http-fx "0.1.6"]
             [duct/compiler.sass "0.2.1"]
             [org.clojure/clojurescript "1.10.339"]
             [re-frame "0.10.6"]
             [reagent "0.8.1"]
             [secretary "1.2.3"]
             [hydrogen/module.cljs "0.1.1"]]
     :dev-deps '[[day8.re-frame/re-frame-10x "0.3.7"]]
     :templates {
                 ;; Client
                 "src/{{dirs}}/client.cljs" (resource "cljs/client.cljs")
                 "src/{{dirs}}/client/home.cljs" (resource "cljs/home.cljs")
                 "src/{{dirs}}/client/routes.cljs" (resource "cljs/routes.cljs")
                 "src/{{dirs}}/client/todo.cljs" (resource "cljs/todo.cljs")
                 "src/{{dirs}}/client/view.cljs" (resource "cljs/view.cljs")
                 ;; API
                 "src/{{dirs}}/api/example.clj" (resource "api/example.clj")
                 "src/{{dirs}}/api/util.clj" (resource "api/util.clj")
                 ;; Static
                 "src/{{dirs}}/static/root.clj" (resource "static/root.clj")
                 ;; Resources
                 "resources/{{dirs}}/index.html" (resource "resources/index.html")
                 "resources/{{dirs}}/public/assets/hydrogen-logo-white.svg" (resource "resources/assets/hydrogen-logo-white.svg")
                 "resources/{{dirs}}/public/css/main.scss" (resource "resources/css/main.scss")
                 }
     :modules {:hydrogen.module.cljs/core {}}
     :dirs ["src/{{dirs}}/boundary/adapter"
            "src/{{dirs}}/boundary/port"]
     :repl-options {:host "0.0.0.0"
                    :port 4001}}))

(defn session-profile [{:keys [project-ns]}]
  {:vars {:hydrogen-cljs-session? true
          :cascading-routes (gen-cascading-routes project-ns ["static/root"
                                                              "api/config"
                                                              "api/example"])}
   :deps '[[duct/middleware.buddy "0.1.0"]
           [magnet/buddy-auth.jwt-oidc "0.5.0"]]
   :templates {
               ;; Client
               "src/{{dirs}}/client/landing.cljs" (resource "cljs/landing.cljs")
               "src/{{dirs}}/client/session.cljs" (resource "cljs/session.cljs")
               "src/{{dirs}}/client/externs.js" (resource "cljs/externs.js")
               ;; API
               "src/{{dirs}}/api/config.clj" (resource "api/config.clj")
               ;; Resources
               "resources/{{dirs}}/public/css/landing.scss" (resource "resources/css/landing.scss")}
   :modules {:hydrogen.module.cljs/session {}}})
