(ns backend.db)

(def db-atom (atom {:usuario nil :transacoes '()}))

;; OK Registrar os dados do usuário; 
;; OK Registrar o consumo de um determinado alimento;
;; OK Registrar a realização de um determinado exercício físico;



;; {:usuario    {:altura 175, :peso 70, :idade 25, :sexo "M"}
;;  :transacoes ({:tipo "alimento", :nome "arroz", :data "2026-06-12", :calorias 200}
;;               {:tipo "exercicio", :nome "corrida", :data "2026-06-12", :calorias 300})}

;; RESPONSABILIDADES DE ESCRITA
(defn cadastrar-usuario! [informacoes]
  (swap! db-atom assoc :usuario informacoes))

(defn cadastrar-registro! [informacoes]
  (swap! db-atom update :transacoes conj informacoes))

(defn buscar-transacoes []
  (:transacoes @db-atom))

(cadastrar-usuario! {:altura 175, :peso 70, :idade 25, :sexo "M"})
(cadastrar-registro! {:tipo "alimento", :nome "xilito", :data "2026-06-12", :calorias 200})
(cadastrar-registro! {:tipo "exercicio", :nome "corrida", :data "2026-06-12", :calorias 300})
(cadastrar-registro! {:tipo "exercicio", :nome "atividades-diversas-na-cama", :data "2026-06-12", :calorias 600})


