(ns backend.db)

(def db-atom (atom {:usuario nil :transacoes '()}))

;; Registrar os dados do usuário; 
;; Registrar o consumo de um determinado alimento;
;; Registrar a realização de um determinado exercício físico;
;; Obter extrato de transações (ganho/perda de calorias);
;; Obter o saldo de calorias.


;; {:usuario    {:altura 175, :peso 70, :idade 25, :sexo "M"}
;;  :transacoes ({:tipo "alimento", :nome "arroz", :data "2026-06-12", :calorias 200}
;;               {:tipo "exercicio", :nome "corrida", :data "2026-06-12", :calorias 300})}

(defn cadastrar-usuario! [informacoes]
  (swap! db-atom assoc :usuario informacoes))

(defn cadastrar-alimentacao [informacoes]
  (swap! db-atom conj :transacoes informacoes))

(cadastrar-alimentacao {:tipo "alimento", :nome "arroz", :data "2026-06-12", :calorias 200})