(ns hyperphor.way.demo.core
  (:gen-class)
  (:require [hyperphor.way.server :as server]
            [hyperphor.way.demo.handler :as handler]
            [hyperphor.multitool.cljcore :as ju]
            [taoensso.timbre :as log]
            [hyperphor.way.config :as config]
            [environ.core :as env]))

(defn -main
  [& [port config]]
  (let [port (or port (env/env :port))
        config (or config "config.edn")]
    (config/read-config config)
    (log/info "Starting server on port" port)
    (server/start (Integer. port) (handler/app))
    (ju/open-url (format "http://localhost:%s" port))
    ))



