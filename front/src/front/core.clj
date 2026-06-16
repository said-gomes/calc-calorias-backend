;; PONTO DE ENTRADA DO FRONT-END
;; ORQUESTRA O MENU E CHAMA AS VIEWS

(ns front.core
  (:gen-class)
  (:require
    [front.views :refer [menu-principal ler-linha executar-opcao]]))

(defn loop-menu []
  (menu-principal)
  (let [opcao (ler-linha "\nEscolha uma opção: ")]
    (executar-opcao opcao)
    (when (not= opcao "0")
      (recur))))

(defn -main [& args]
  (loop-menu))
