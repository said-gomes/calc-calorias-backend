;; PULA 10.2 E 11.1 SE QUISER

;; Obter extrato de transações (ganho/perda de calorias);
;; Obter o saldo de calorias.

(ns backend.handler
  (:require [backend.db :refer [buscar-transacoes
                                buscar-usuario
                                cadastrar-registro!
                                cadastrar-usuario!]]
            [backend.request :refer [buscar-ganho-calorico
                                     buscar-gasto-calorico]]
            [cheshire.core :as json]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))


;; {:nome "Said", :altura 175, :peso 70, :idade 25, :sexo "M"}

;;FUNCOES P/ CADASTRO E REQUISICAO (JUNCAO DO REQUEST DA API + METODO DO DB)

;; mensagem q vem do front
;; {:tipo "exercicio", :nome "corrida", :duracao 30, :data "2026-02-12"}

;; mensagem pro atom
;; {:tipo "exercicio", :nome "corrida", :data "2026-06-12", :calorias 200}

(defn realizar-cadastro-exercicio [mensagem]
  (let [mensagem (json/parse-string (:body mensagem) true)
        tipo (:tipo mensagem)
        exercicio (:nome mensagem)
        duracao (:duracao mensagem)
        data (:data mensagem)
        calorias (buscar-gasto-calorico exercicio duracao)
        payload {:tipo tipo, :nome exercicio, :data data, :calorias calorias}]
    (cadastrar-registro! payload)))


(defn realizar-cadastro-alimento [mensagem]
  (let [mensagem (json/parse-string (:body mensagem) true)
        tipo (:tipo mensagem)
        alimento (:nome mensagem)
        data (:data mensagem)
        calorias (buscar-ganho-calorico alimento)
        payload {:tipo tipo, :nome alimento, :data data, :calorias calorias}]
    (cadastrar-registro! payload)))



;; FUNCAO AUXILIAR P/ EXTRATO FICAR MAIS LEGIVEL
(defn filtro-aux [col tipo inicio fim]
  (and (= (:tipo col) tipo)
       (<= (compare inicio (:data col)) 0)
       (<= (compare (:data col) fim) 0)))

(defn extrato-transacoes [inicio fim]
  (let [transacoes (buscar-transacoes)
        alimentos (filter #(filtro-aux % "alimento" inicio fim) transacoes)
        exercicios (filter #(filtro-aux % "exercicio" inicio fim) transacoes)]

    {:exercicio exercicios,
     :alimento alimentos}))

(defn saldo-calorias [inicio fim]
  (let [transacoes (extrato-transacoes inicio fim)
        calorias-adquiridas (reduce + (map #(:calorias %) (:alimento transacoes)))
        calorias-gastas (reduce + (map #(:calorias %) (:exercicio transacoes)))]

    {:calorias-adquiridas calorias-adquiridas, :calorias calorias-gastas}))

(defroutes app-routes
  (GET "/" [] "Hello!")

  (POST "/usuario" mensagem (let [usuario (json/parse-string (:body mensagem) true)]
                              (cadastrar-usuario! usuario)))

  (GET "/usuario" [] (json/generate-string (buscar-usuario)))

  (POST "/alimento" mensagem (realizar-cadastro-alimento mensagem))

  (POST "/exercicio" mensagem (realizar-cadastro-exercicio mensagem))

  (GET "/extrato" mensagem (let [params (:query-params mensagem)
                                 inicio (get params "inicio")
                                 fim    (get params "fim")]
                             (json/generate-string (extrato-transacoes inicio fim))))
  
  (GET "/saldo" mensagem (let [params (:query-params mensagem)
                               inicio (get params "inicio")
                               fim    (get params "fim")]
                          (json/generate-string (saldo-calorias inicio fim))))

  (route/not-found "Not Found"))


(def app
  (wrap-defaults app-routes site-defaults))
  