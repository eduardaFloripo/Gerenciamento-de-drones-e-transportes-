# Gerenciamento-de-drones-e-transportes-

# 🚁 ACME Air Drones

## 📌 Sobre o Projeto

O **ACME Air Drones** é um sistema desktop desenvolvido em **Java** utilizando **Swing**, que simula o gerenciamento de drones e transportes em uma empresa de logística aérea.

O sistema permite cadastrar drones, cadastrar transportes, processar entregas, gerar relatórios e salvar/carregar dados em arquivos.

---

## 🎯 Objetivo

Gerenciar uma frota de drones e transportes permitindo:

- Cadastro e controle de drones  
- Cadastro e controle de transportes  
- Alocação automática de drones  
- Controle de status das entregas  
- Persistência em arquivos  

---

## 🧱 Estrutura do Projeto

### 📂 aplicacao
Responsável pela interface gráfica e fluxo do sistema.

Inclui:
- Tela principal  
- Cadastro de drones  
- Cadastro de transportes  
- Processamento de transportes pendentes  
- Alteração de situação  
- Relatórios  
- Leitura e gravação de arquivos  

---

### 📂 dados
Responsável pelo modelo de domínio e regras de negócio.

---

## 🚁 Hierarquia de Drones
Drone (abstrato)
├ DronePessoal
└ DroneCarga (abstrato)
├ DroneCargaInanimada
└ DroneCargaViva

---

## 📦 Hierarquia de Transportes
Transporte (abstrato)
├ TransportePessoal
├ TransporteCargaInanimada
└ TransporteCargaViva


---

## 📊 Estados do Transporte

Enum `Estado`:

- PENDENTE  
- ALOCADO  
- TERMINADO  
- CANCELADO  

---

## ⚙️ Regras de Negócio

✔ Todo transporte nasce como **PENDENTE**  
✔ Transportes entram em fila de processamento  
✔ Drones alocados ficam indisponíveis  

✔ Custo considera:
- Custo do drone por km  
- Distância geográfica real  
- Acréscimos por tipo de transporte  

---

## 📍 Cálculo de Distância

O sistema utiliza coordenadas geográficas para calcular distância entre origem e destino, simulando cenários reais de logística.

---

## 🌡 Regras Específicas

### 👥 Transporte Pessoal
Custo adicional proporcional ao número de passageiros.

### 📦 Transporte de Carga Inanimada
Taxa extra para cargas perigosas.

### 🐾 Transporte de Carga Viva
Taxa extra dependendo da faixa de temperatura exigida.

---

## 💾 Persistência

✔ Salvar dados em TXT  
✔ Carregar dados TXT  
✔ Importar CSV  

---

## 💻 Tecnologias Utilizadas

- Java  
- Java Swing  
- Programação Orientada a Objetos  
- ArrayList  
- Queue (fila de transportes pendentes)  
- Manipulação de Arquivos  

---

## 🧠 Conceitos Aplicados

- Abstração  
- Herança  
- Polimorfismo  
- Encapsulamento  
- Enum  
- Estruturas de Dados  
- Modelagem de Domínio  

---

## ▶️ Execução

O sistema inicia pela classe:
Que executa: ACMEAirDrones

Responsável por inicializar a aplicação e abrir a interface principal (`TelaPrincipal`).

---

## 🚀 Possíveis Melhorias Futuras

- Integração com banco de dados  
- API REST  
- Versão Web  
- Sistema de login  
- Algoritmo inteligente de alocação de drones  
