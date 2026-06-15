(ns backend.request
  (:require [clj-http.client :as client]
            [clojure.edn]
            [cheshire.core :as json]))

; CONFIGURACOES
(def config (clojure.edn/read-string (slurp "config.edn")))
; ATIVIDADE FISICA (NINJA API)
(def chave-api-cal (:api-cal-key config))
(def url-cal (:url-api-cal config))
; ATIVIDADE 
(def chave-api-food (:api-food-key config))
(def url-food (:url-api-food config))


;; FETCH API NUMERO CALORIAS

;; AUX 1
(defn req-gasto-cal [atividade duracao]
  (client/get url-cal {:headers {"X-Api-Key" chave-api-cal}
                       :query-params {:activity atividade,
                                      :duration duracao}}))
;; AUX 2
(defn filtragem-dados-gasto-cal [resposta]
  (:total_calories (first (json/parse-string (:body resposta) true))))

;; CONSULTA API EXERCICIO
(defn buscar-gasto-calorico [atividade duracao]
  (filtragem-dados-gasto-cal (req-gasto-cal atividade duracao)))


;; FETCH API NUMERO CALORIAS
;; AUX 1
(defn req-ganho-cal [alimento]
  (client/get url-food {
                        :query-params {:api_key chave-api-food,
                                       :query alimento}}))
;; AUX 2
(defn filtragem-dados-valor-cal [resposta]
  (let [info-alimento (filter #(= (:nutrientName %) "Energy")
                              (:foodNutrients (first (:foods (json/parse-string (:body resposta) true)))))
        valor-calorias (:value (first info-alimento))]
    valor-calorias
    ))

;; CONSULTA API ALIMENTO
(defn buscar-ganho-calorico [alimento]
  (filtragem-dados-valor-cal (req-ganho-cal alimento)))

;; 
;; (def lista-atv ["running", "walking",
;;                 "cycling" "swimming"
;;                 "jump rope", "rowing",
;;                 "weight lifting", "push-ups",
;;                 "sit-ups", "yoga", "pilates",
;;                 "soccer", "basketball",
;;                 "volleyball", "tennis",
;;                 "martial arts", "dancing",
;;                 "hiking", "gardening",
;;                 "cleaning", "boxing",
;;                 "crossfit", "skiing",
;;                 "surfing", "golf"])

;; TESTE 
;; (buscar-gasto-calorico "running" 60)
;; (buscar-ganho-calorico "bread")