(ns backend.request
  (:require [clj-http.client :as client]))

;TODO: perguntar se esse metodo ta ok

(def chave-api-cal "2XY5KmcHbrtD6mBdkRp3Es9KHMmFiiHifvQnOArh")

(def url-api-cal "https://api.api-ninjas.com/v1/caloriesburned")

(def chave-api-food "iz8H40bOBA4JxUd1S6YWL0uxagxp5udJ21o9vpu8")

(def url-api-food "https://api.api-ninjas.com/v1/caloriesburned")

;; ;API CALL EXEMPLE
;; (client/get url-api-cal
;;             {:query-params {"activity" "running"
;;                             "weight"   70
;;                             "duration" 30}
;;              :headers      {"X-Api-Key" chave-api-cal}})

