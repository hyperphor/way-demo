(ns hyperphor.way.demo.handler
  (:require [compojure.core :refer [defroutes context GET POST make-route routes]]
            [ring.util.response :as response]
            [hyperphor.way.demo.dbpedia :as dbpedia]
            [hyperphor.way.handler :as wh]
            [hyperphor.way.views.html :as html]
            )
  (:use [hiccup.core])
  )

;;; Sample server-side rendering
(defn dbpedia-view
  [id]
  (response/content-type
   (wh/content-response
    (html/html-frame
     {} (str "DBPedia/" id)             ;TODO extend html-frame to support hierarchical paths
     [:div
      (dbpedia/entity-content id)
      ]))
   "text/html"))

(defroutes site-routes
  (GET "/dbpedia/:id" [id] (dbpedia-view id) )
  )

;;; Warning. Do not use "(def app ...)", config isn't necessarily right at compile time
(defn app
  []
  (wh/app site-routes (routes)))

