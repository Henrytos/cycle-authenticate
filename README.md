Com certeza. Vou aprimorar o seu `README.md` para ser mais profissional, claro e direto, focando na proposta de valor e na arquitetura do projeto.

-----

# `cycle-authenticate` | Sistema de Autenticação JWT

Este projeto implementa um ciclo completo de autenticação e autorização, utilizando **Spring Boot** no *backend* e seguindo as melhores práticas de design de software. O sistema é construído sobre os princípios do **Domain-Driven Design (DDD)** e da **Arquitetura Hexagonal**.

## 🚀 Tecnologias e Arquitetura

O foco do projeto é na clareza e manutenibilidade do código:

* **Backend:** Spring Boot (Java 21).
* **Segurança:** Implementação de tokens **JWT** (JSON Web Token) para autenticação *stateless*.
* **Persistência:** JPA e Flyway para migrações de banco de dados.
* **Design:** Aplicação rigorosa dos padrões **DDD** (separação clara de domínio, aplicação e infraestrutura) dentro da **Arquitetura Hexagonal** (ports & adapters).

## 🗄️ Modelagem de Dados

A estrutura de classes e as entidades do domínio são essenciais para entender o fluxo de dados.

* **Entidades:**
    * `User`: Entidade central que armazena dados de cadastro e credenciais.
    * `Token`: Entidade para gestão de *Refresh Tokens* e controle de sessão (se aplicável).

## 🌐 Endpoints da API (Rotas HTTP)

A API expõe os seguintes *endpoints* para gerenciar o ciclo de vida do usuário e da sessão:

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| **`POST`** | `/users` | **Cadastro de Novo Usuário**. |
| **`POST`** | `/authenticate` | **Login**. Gera um novo JWT (e Refresh Token). |
| **`GET`** | `/me` | **Validação/Informação do Usuário**. Requer um JWT válido no *header*. |
| **`POST`** | `/refreshToken` | **Renovação de Token**. Gera um novo JWT a partir de um *Refresh Token* existente. |

### Detalhes dos Endpoints

#### **1. Cadastro (`POST /users`)**

| Campo | Tipo | Obrigatório | Notas |
| :--- | :--- | :--- | :--- |
| `username` | String | Sim | Nome de usuário. |
| `password` | String | Sim | Senha (armazenada com *hashing*). |
| `email` | String | Sim | Endereço de e-mail único. |
| `date_of_birth` | Date | Opcional | Data de nascimento. |
| `avatar` | String | Opcional | URL ou ID do avatar. |

#### **2. Login (`POST /authenticate`)**

| Campo | Tipo | Obrigatório | Notas |
| :--- | :--- | :--- | :--- | 
| `email` | String | Sim | E-mail do usuário. |
| `password` | String | Sim | Senha do usuário. |

#### **3. Informações da Sessão (`GET /me`)**

| Tipo | Header | Exemplo |
| :--- | :--- | :--- |
| **Autorização** | `Authorization` | `Bearer <TOKEN_JWT_VALIDO>` |

#### **4. Renovação de Token (`POST /refreshToken`)**

| Tipo | Header | Exemplo |
| :--- | :--- | :--- |
| **Autorização** | `Authorization` | `Bearer <REFRESH_TOKEN_JWT>` |

## 🛠️ Como Executar

(Adicione aqui instruções básicas de como clonar, configurar o ambiente (`.env` ou `application.properties`) e rodar a aplicação via Maven ou Docker.)

`` 
docker compose up
``