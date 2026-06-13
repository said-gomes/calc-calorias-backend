;; PULA 10.2 E 11.1 SE QUISER

;; Obter extrato de transações (ganho/perda de calorias);
;; Obter o saldo de calorias.

(ns backend.handler
  (:require [backend.db :refer [buscar-transacoes]]
            [cheshire.core :as json]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World") 
  (GET "/saldo" [] (json/generate-string {:saldo 0}))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))


(defn extrato-transacoes []
  (let [transacoes (buscar-transacoes) 
        alimentos (filter #(= (:tipo %) "alimento") transacoes) 
        exercicio (filter #(= (:tipo %) "exercicio") transacoes) 
        calorias-adquiridas (reduce + (map :calorias alimentos)) 
        calorias-perdidas (reduce + (map :calorias exercicio)) 
        
        ]
    (println calorias-adquiridas)
    (println calorias-perdidas)
    ))

(extrato-transacoes)
