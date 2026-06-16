;; MODULO DE REQUISICOES HTTP PARA A API

(ns front.http
  (:require [cheshire.core :as json]
            [clj-http.client :as client]))

;; URL BASE DA APP
(def url "http://localhost:3001")

;; {:nome "Said", :altura 175, :peso 70, :idade 25, :sexo "M"}
(defn cadastrar-usuario [nome altura peso idade sexo]
  (client/post (str url "/usuario")
               {:body (json/generate-string {:nome nome :altura altura :peso peso :idade idade :sexo sexo})
                :content-type :json}))

(defn buscar-usuario []
  (client/get (str url "/usuario")
              {:as :json}))

;; {:tipo "alimento", :nome "arroz", :data "2026-06-15"}
(defn registrar-alimento [nome data]
  (client/post (str url "/alimento")
               {:body (json/generate-string {:tipo "alimento" :nome nome :data data})
                :content-type :json}))

;; {:tipo "exercicio", :nome "running", :duracao 30, :data "2026-06-15"}
(defn registrar-exercicio [nome duracao data]
  (client/post (str url "/exercicio")
               {:body (json/generate-string {:tipo "exercicio" :nome nome :duracao duracao :data data})
                :content-type :json}))

(defn buscar-extrato [inicio fim]
  (client/get (str url "/extrato")
              {:query-params {:inicio inicio :fim fim}
               :as :json}))

(defn buscar-saldo [inicio fim]
  (client/get (str url "/saldo")
              {:query-params {:inicio inicio :fim fim}
               :as :json}))
