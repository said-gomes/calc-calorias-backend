;; PULA 10.2 E 11.1 SE QUISER

;; Obter extrato de transações (ganho/perda de calorias);
;; Obter o saldo de calorias.

(ns backend.handler
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [cheshire.core :as json]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World") 
  (GET "/saldo" [] (json/generate-string {:saldo 0}))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
