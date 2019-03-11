(ns hydrogen.cljs.duct-template
    (:require [clojure.java.io :as io]))

(defn resource [path]
  (io/resource (str "hydrogen/" path)))

(defn core-profile [_]
  {:vars {:hydrogen-cljs-core? true}
   :deps '[[duct/module.cljs "0.4.0"]
           [org.clojure/clojurescript "1.10.339"]
           [duct/compiler.sass "0.2.1"]
           [cljs-ajax "0.7.5"]
           [re-frame "0.10.6"]
           [reagent "0.8.1"]
           [day8.re-frame/http-fx "0.1.6"]
           [secretary "1.2.3"]]
   :templates {;;"src/{{dirs}}/client/view.cljs" (resource "cljs/view.cljs")
               "src/{{dirs}}/client.cljs" (resource "cljs/client.cljs")
               "src/{{dirs}}/handler/root.clj" (resource "handler/root.clj")
               "resources/{{dirs}}/index.html" (resource "resources/index.html")
               "src/{{dirs}}/client/home.cljs" (resource "cljs/home.cljs")
               "src/{{dirs}}/client/routes.cljs" (resource "cljs/routes.cljs")
               "src/{{dirs}}/client/todo.cljs" (resource "cljs/todo.cljs")
               "src/{{dirs}}/client/view.cljs" (resource "cljs/view.cljs")
               "resources/{{dirs}}/public/assets/hydrogen-logo-white.svg" (resource "resources/assets/hydrogen-logo-white.svg")
               "resources/{{dirs}}/public/css/landing.scss" (resource "resources/css/landing.scss")
               "resources/{{dirs}}/public/css/main.scss" (resource "resources/css/main.scss")
               ;;"src/{{dirs}}/client/landing.cljs" (resource "cljs/landing.cljs")
               ;;"src/{{dirs}}/client/routes.cljs" (resource "cljs/routes.cljs")
               }})

(defn session-profile [_]
  {:vars {:hydrogen-cljs-session? true}})

(defn example.todo-profile [_]
  {:templates {"src/{{dirs}}/client/todo.cljs" (resource "cljs/todo.cljs")}})
