# Sobre o Curso

A arquitetura de microsserviços, embora baseada em ideias que remontam à década de 1960, ganhou força e destaque a partir de 2011, transformando profundamente a forma como projetamos e desenvolvemos aplicações modernas. Ao adotar princípios como independência, escalabilidade e resiliência, os microsserviços se tornaram a espinha dorsal de sistemas distribuídos robustos e de alta disponibilidade.

Neste bootcamp, você será introduzido aos fundamentos teóricos e práticos da construção de microsserviços. Vamos explorar os principais conceitos, entender os benefícios e desafios dessa abordagem, e analisar os trade-offs envolvidos no desenvolvimento de sistemas desacoplados.

Na parte prática, construiremos uma arquitetura completa baseada em microsserviços autônomos, gerenciáveis e resilientes, utilizando a linguagem Java e o framework Spring Boot. Abordaremos integrações via chamadas síncronas e assíncronas, aplicando padrões de comunicação e mensageria com tecnologias amplamente utilizadas no mercado.

Para simular um ambiente em nuvem real, utilizaremos o LocalStack como plataforma local que emula serviços da AWS, como SQS (Simple Queue Service) e SNS (Simple Notification Service), permitindo testar e validar nossos serviços de forma eficiente e sem custos com infraestrutura.

Ao final do curso, você estará preparado para projetar, desenvolver e manter sistemas baseados em microsserviços, dominando conceitos modernos de mensageria, escalabilidade e arquitetura distribuída.

# O que iremos apreender

🔹 Fundamentos e Arquitetura
Entender o que são microsserviços, seus benefícios, desafios e trade-offs
Comparar arquiteturas monolíticas vs. distribuídas
Estruturar uma arquitetura orientada a eventos com foco em escalabilidade e resiliência

🔹 Desenvolvimento com Java e Spring Boot
Criar serviços independentes utilizando o ecossistema Spring Boot
Trabalhar com Spring Web, Spring Data JPA e Spring Cloud
Configurar profiles, propriedades e variáveis de ambiente para serviços desacoplados

🔹 Comunicação entre Microsserviços
Implementar chamadas síncronas com REST (HTTP/JSON) usando OpenFeign
Utilizar mensageria assíncrona com AWS SQS/SNS, simuladas com LocalStack

# Documento Técnicos - Microserviços 

## Microserviço de Conta (Account Service)

### 1. Visão Geral
Este documento apresenta o design do Microserviço de Conta, que é responsável por criar e gerenciar as contas bancárias dos clientes, incluindo a consulta de saldos e a atualização de valores. Ele se comunica com o Serviço de Cliente para manter a vinculação correta entre as contas e seus donos.

### 2. Entidades e Atributos Account
   
* accountId: Identificador único da conta. (Tipo: Long)
* customerId: Identificador do cliente ao qual a conta pertence. (Tipo: Long)
* accountNumber: Número da conta bancária. (Tipo: String)
* balance: O saldo atual da conta. (Tipo: BigDecimal)
* accountType: O tipo de conta (Corrente ou Poupança). (Tipo: Enum AccountType)
* accountStatus: O status da conta

### AccountStatus(Enum)
* ACTIVE
* INACTIVE
* CLOSED

### AccountType (Enum)
* CHECKING: Conta Corrente.
* SAVINGS: Conta Poupança.

## Microserviço de Client (Client Service)

### 1. Visão Geral
Este documento detalha o design e a estrutura do Microserviço de Cliente, responsável por gerenciar todas as informações e operações relacionadas aos clientes do banco. O serviço foi projetado para ser autônomo, garantindo a separação de responsabilidades e facilitando a escalabilidade.

### 2. Entidades e Atributos Client

* clientId: Identificador único do cliente. (Tipo: Long)
* name: Nome completo do cliente. (Tipo: String)
* email: Endereço de e-mail do cliente, usado para comunicação. (Tipo: String)
* cpf: Cadastro de Pessoa Física, identificador legal. (Tipo: String)


## Microserviço de Transação (Transaction Service)

### 1. Visão Geral
Este documento descreve o Microserviço de Transação, o motor do sistema bancário. Ele é responsável por processar todas as operações financeiras como saques, depósitos e transferências. A comunicação com o serviço de notificação é feita de forma assíncrona para garantir resiliência e desacoplamento.

### 2. Entidades e Atributos Transaction

* transactionId: Identificador único da transação. (Tipo: String)
* sourceAccountId: Conta de origem da transação. (Tipo: String)
* destinationAccountId: Conta de destino da transação. (Tipo: String)
* amount: O valor da transação. (Tipo: BigDecimal)
* transactionType: O tipo de operação realizada. (Tipo: Enum TransactionType)
* timestamp: A data e hora em que a transação ocorreu. (Tipo: LocalDateTime)
* status: O estado atual da transação. (Tipo: Enum TransactionStatus)

### TransactionType (Enum)
DEPOSIT: Depósito.
WITHDRAW: Saque.
TRANSFER: Transferência.

### TransactionStatus (Enum)
* PENDING: Em processamento.
* COMPLETED: Concluída com sucesso.
* FAILED: Falhou.

## Microserviço de Notificação (Notification Service)

### 1. Visão Geral
Este documento descreve o Microserviço de Notificação, cuja única responsabilidade é enviar alertas aos clientes sobre eventos importantes, como transações. O serviço opera de forma assíncrona, consumindo mensagens de uma fila para garantir a entrega confiável das notificações.

### 2. Endidades e Atributos Notification

* notificationId: Identificador único da notificação. (Tipo: String)
* customerId: Identificador do cliente que deve receber a notificação. (Tipo: String)
* message: O conteúdo da mensagem de notificação. (Tipo: String)
* timestamp: Data e hora em que a notificação foi criada. (Tipo: LocalDateTime)
* status: O estado da notificação (enviada ou falhou). (Tipo: Enum NotificationStatus)

### NotificationStatus (Enum)
* SENT: A notificação foi enviada com sucesso.
* FAILED: Ocorreu uma falha no envio da notificação.

### Diagrama de Classe

![Logo Spring](https://github.com/via-results/bootcamp-acme-bank/blob/main/Captura%20de%20Tela%202025-08-05%20a%CC%80s%2014.59.05.png)

# Regras de Negócio

### Clients
No nosso caso de uso prático, tremos dois tipos de clientes, os comuns e lojistas, e serão identificados pelo seu tipo de documento, CPF e ou CNPJ. Seguindo essa premissa
teremos as seguintes regras estabelecidas para os fluxos de negócios relacionados aos clientes:
* Não será permitido a inclusão de clientes duplicados, ou seja, o sistema deverá emitir uma mensagem ao usuário que o cliente já existe e não pode ser cadastrado novamente,
  para isso, o sistema deverá validar essa regra observando o atributo e-mail;
* Os clientes lojista não podem realizar transferência de valores, apenas recebem valores de clientes comuns;
* Clientes comuns, deverm realizar todo tipo de transação entres si;
* Antes de qualquer transação que envolva, saques e ou transferência de valores, o salda da conta de origem deve ser verificado, ou seja para casos de saldos insuficientes
  a transação não poderá ser realizada;
* A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o valor deve voltar para a conta de origem;
* Ao final da transação, o usuário comum ou lojista precisa ser notificação sobre a transação, essa notificação pode ser realizada via e-mail;

#### Sugestão de Payload de Transação

   POST /transfer
   Content-Type: application/json
   
   {
     "amout": 100.0,
     "payer": 4, -> origin accountNumber
     "payee": 15 -> destination accountNumber
   }
  
### Account
### Notifications
### Transactions
