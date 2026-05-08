# RabbitMQ Banking Transactions

Sistema bancário assíncrono baseado em eventos, desenvolvido em Java 17 com RabbitMQ. O projeto simula transações bancárias (depósitos e saques), onde um Producer valida regras de negócio e publica eventos, e um Consumer processa as mensagens em tempo real.

## Tecnologias utilizadas

* Java 17
* Maven
* RabbitMQ
* Docker e Docker Compose
* Gson
* JUnit 5

---

## Pré-requisitos

Antes de iniciar, instale:

* Java JDK 17+
* Maven
* Docker e Docker Compose

---

## Configuração do ambiente

O projeto utiliza variáveis de ambiente para conexão com o RabbitMQ.

1. Crie um arquivo `.env` na raiz dos módulos `producer` e `consumer`, seguindo o padrão do `.env.example`.


---

## Como executar

### 1. Subir o RabbitMQ

Na raiz do projeto:

```bash
  docker-compose up -d
```

---

### 2. Executar o Consumer

Em um terminal:

```bash
    cd consumer
    mvn clean compile exec:java -Dexec.mainClass="ConsumerMain"
```

O consumer ficará aguardando mensagens.

---

### 3. Executar o Producer

Em outro terminal:

```bash
    cd producer
    mvn clean compile exec:java -Dexec.mainClass="Main"
```

O producer irá simular transações e publicá-las na fila.

---

## Testes

Os testes unitários validam as regras de negócio da conta.

Para executar:

```bash
    cd producer
    mvn test
```

---

## Arquitetura

O projeto é dividido em três módulos:

**Producer**

* Valida operações bancárias
* Atualiza saldo em memória
* Serializa eventos em JSON com Gson
* Publica mensagens no RabbitMQ

**Consumer**

* Consome mensagens da fila
* Desserializa eventos JSON
* Processa transações
* Gera notificações


**Shared**

Módulo compartilhado entre Producer e Consumer contendo:

* TransactionEventDTO (modelo de evento)
* TransactionType (enum de tipos de transação)
* RabbitMqConfig (configuração de conexão com RabbitMQ)

