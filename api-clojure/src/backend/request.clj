(ns backend.request
  (:require [clj-http.client :as client]
            [clojure.edn]))

; CONFIGURACOES
(def config (clojure.edn/read-string (slurp "config.edn")))
(def chave-api-cal (:api-cal-key config))
(def chave-api-food (:api-food-key config))
(def url-food (:url-api-cal config))
(def url-cal (:url-api-cal config))
