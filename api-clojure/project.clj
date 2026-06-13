(defproject api-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/tools.cli	"0.4.1"]
                 [clj-http "3.9.1"]
                 [cheshire "5.8.1"]] ;;LIDAR COM JSON
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler backend.handler/app} ;;PONTO DE ENTRADA
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
