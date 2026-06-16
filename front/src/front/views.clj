;; MODULO DE VIEWS E FLUXOS DO USUARIO

(ns front.views
  (:require
    [front.http :as http]))

;; LISTA DE ATIVIDADES SUPORTADAS PELA API 
(def lista-atv ["running" "walking" "cycling" "swimming"
                "jump rope" "rowing" "weight lifting" "push-ups"
                "sit-ups" "yoga" "pilates" "soccer"
                "basketball" "volleyball" "tennis" "martial arts"
                "dancing" "hiking" "gardening" "cleaning"
                "boxing" "crossfit" "skiing" "surfing" "golf"])

;; TRADUCAO DAS ATIVIDADES
(def traducao-atv
  {"running"        "Corrida"
   "walking"        "Caminhada"
   "cycling"        "Ciclismo"
   "swimming"       "Natação"
   "jump rope"      "Pular corda"
   "rowing"         "Remo"
   "weight lifting" "Musculação"
   "push-ups"       "Flexão"
   "sit-ups"        "Abdominal"
   "yoga"           "Yoga"
   "pilates"        "Pilates"
   "soccer"         "Futebol"
   "basketball"     "Basquete"
   "volleyball"     "Vôlei"
   "tennis"         "Tênis"
   "martial arts"   "Artes marciais"
   "dancing"        "Dança"
   "hiking"         "Trilha"
   "gardening"      "Jardinagem"
   "cleaning"       "Faxina"
   "boxing"         "Boxe"
   "crossfit"       "Crossfit"
   "skiing"         "Esqui"
   "surfing"        "Surf"
   "golf"           "Golfe"})

(defn traduzir [nome-en]
  (get traducao-atv nome-en nome-en))

;; LISTA NUMERADA 
(defn exibir-lista-atividades []
  (dorun
   (map-indexed
    #(println (str (inc %1) ". " (traduzir %2))) lista-atv)))

;; FUNC AUXILIAR P/ LEITURA DE INPUT
(defn ler-linha [prompt]
  (print prompt)
  (flush)
  (read-line))

;;VIEW DO MENU PRINCIPAL
(defn menu-principal []
  (println "\n=== Calculadora de Calorias ===")
  (println "1. Cadastrar usuário")
  (println "2. Consultar usuário")
  (println "3. Registrar alimento")
  (println "4. Registrar exercício")
  (println "5. Consultar extrato")
  (println "6. Consultar saldo")
  (println "0. Sair"))

;; FUNC P/ INTERACAO COM USUARIO

(defn cadastrar-usuario []
  (let [nome   (ler-linha "Nome: ")
        altura (Integer/parseInt (ler-linha "Altura (cm): "))
        peso   (Double/parseDouble (ler-linha "Peso (kg): "))
        idade  (Integer/parseInt (ler-linha "Idade: "))
        sexo   (ler-linha "Sexo (M/F): ")]
    (http/cadastrar-usuario nome altura peso idade sexo)
    (println "Usuário cadastrado!")))

(defn exibir-usuario [usuario]
  (println (str "Nome: "   (:nome usuario)))
  (println (str "Altura: " (:altura usuario) " cm"))
  (println (str "Peso: "   (:peso usuario) " kg"))
  (println (str "Idade: "  (:idade usuario)))
  (println (str "Sexo: "   (:sexo usuario))))

(defn exibir-transacao [usuario]
  (println (str (traduzir (:nome usuario)) " - " (:data usuario) " - " (:calorias usuario) " kcal")))

(defn exibir-extrato [extrato]
  (println "\nAlimentos:")
  (dorun (map exibir-transacao (:alimento extrato)))
  (println "\nExercícios:")
  (dorun (map exibir-transacao (:exercicio extrato))))

(defn exibir-saldo [saldo]
  (println (str "Calorias adquiridas: " (:calorias-adquiridas saldo) " kcal"))
  (println (str "Calorias gastas: "     (:calorias saldo) " kcal")))

(defn get-usuario []
  (let [resp (:body (http/buscar-usuario))]
    (exibir-usuario resp)))

(defn cadastrar-alimento []
  (let [nome (ler-linha "Nome do alimento: ")
        data (ler-linha "Data (YYYY-MM-DD): ")]
    (http/registrar-alimento nome data)
    (println "Alimento registrado!")))

;; AJUSTA INDICE, JA Q A LISTA COMECA DO 0
(defn cadastrar-exercicio []
  (exibir-lista-atividades)
  (let [opcao   (Integer/parseInt (ler-linha "Escolha o número da atividade: "))
        nome    (nth lista-atv (dec opcao))
        duracao (Integer/parseInt (ler-linha "Duração (minutos): "))
        data    (ler-linha "Data (YYYY-MM-DD): ")]
    (http/registrar-exercicio nome duracao data)
    (println "Exercício registrado!")))

(defn get-extrato []
  (let [inicio (ler-linha "Data início (YYYY-MM-DD): ")
        fim    (ler-linha "Data fim (YYYY-MM-DD): ")
        resp   (:body (http/buscar-extrato inicio fim))]
    (exibir-extrato resp)))

(defn get-saldo []
  (let [inicio (ler-linha "Data início (YYYY-MM-DD): ")
        fim    (ler-linha "Data fim (YYYY-MM-DD): ")
        resp   (:body (http/buscar-saldo inicio fim))]
    (exibir-saldo resp)))


(defn executar-opcao [opcao]
  (case opcao
    "1" (cadastrar-usuario)
    "2" (get-usuario)
    "3" (cadastrar-alimento)
    "4" (cadastrar-exercicio)
    "5" (get-extrato)
    "6" (get-saldo)
    "0" (println "Até logo!")
    (println "Opção inválida.")))
