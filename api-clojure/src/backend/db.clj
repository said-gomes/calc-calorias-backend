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

;;ESSAS FUNCOES REALMENTE PRECISAM DE ! NA NOTACAO?
(defn buscar-transacoes []
  (:transacoes @db-atom))

(defn buscar-usuario []
  (:usuario @db-atom))

;; MOCK DE DADOS

;; (cadastrar-usuario! {:altura 175, :peso 70, :idade 25, :sexo "M"})
;; (cadastrar-registro! {:tipo "alimento", :nome "xilito", :data "2026-06-12", :calorias 200})
;; (cadastrar-registro! {:tipo "exercicio", :nome "corrida", :data "2026-06-12", :calorias 300})
;; (cadastrar-registro! {:tipo "exercicio", :nome "atividades-diversas-na-cama", :data "2026-06-12", :calorias 600})
;; (cadastrar-registro! {:tipo "exercicio", :nome "correr-de-ladrao", :data "2026-04-12", :calorias 100})
;; (cadastrar-registro! {:tipo "alimento", :nome "10-chopps", :data "2026-04-12", :calorias 100})


