# 📚 LiterAlura API

## 📋 Descrição do Projeto

O LiterAlura é uma aplicação Java que funciona como um catálogo de livros integrado à API pública Gutendex (Project Gutenberg).

O sistema permite:

- Buscar livros por título diretamente na API
- Persistir os resultados em um banco relacional
- Listar livros armazenados
- Filtrar livros por idioma

Este projeto foi desenvolvido como parte do programa Oracle Next Education (ONE) em parceria com a Alura, com foco em:

- consumo de APIs REST
- manipulação de JSON
- persistência de dados com JPA/Hibernate
- arquitetura em camadas com Spring Boot

---

## 🛠️ Tech Stack

Tecnologias utilizadas no projeto:

- Java 17 (LTS)
- Spring Boot 3
- Spring Data JPA
- Hibernate (ORM)
- PostgreSQL 16
- Jackson (JSON Processing)
- Maven (Build Tool)
- Git
- GitHub

---

## 🏗️ Arquitetura do Projeto

Estrutura principal do projeto:

src/main/java/br/com/alura/literalura

dto
└── BookResponse

model
├── Autor
└── Livro

repository
├── AutorRepository
└── LivroRepository

service
├── ConsumoApi
├── ConverteDados
└── LivroService

LiteraluraApplication

Fluxo da aplicação:

Menu CLI
↓
LivroService
↓
ConsumoApi → Gutendex API
↓
ConverteDados (JSON → DTO)
↓
Repository (JPA)
↓
PostgreSQL

---

## 🚀 Como Rodar Localmente

Clone o repositório:

git clone https://github.com/johnny-dll/Alure_ONE_G9_Trilha_Java_Spring_Framework_-_Challenge_01_de_02.git

Acesse a pasta do projeto:

cd literalura

Configure o banco de dados no arquivo:

src/main/resources/application.properties

Execute a aplicação:

./mvnw spring-boot:run

Ou execute pelo IntelliJ:

Run → LiteraluraApplication

---

## 💻 Menu Interativo (Console)

A aplicação roda via interface de linha de comando (CLI).

Menu principal:

===== LITERALURA =====

1 - Buscar livro por título
2 - Listar livros registrados
3 - Listar livros por idioma
0 - Sair

---

## 🔎 Buscar livro por título

Consulta a API Gutendex:

https://gutendex.com/books/?search={titulo}

Fluxo da busca:

1. Consome a API
2. Obtém o primeiro resultado
3. Converte o JSON para DTO
4. Cria entidades Autor e Livro
5. Persiste os dados no banco

---

## 📚 Listar livros registrados

Exibe todos os livros salvos no banco local.

Exemplo de saída:

Título: Dracula
Autor: Bram Stoker
Idioma: en
Downloads: 12345

---

## 🌎 Listar livros por idioma

Permite filtrar livros pelo idioma registrado.

Exemplos de códigos de idioma:

en → Inglês  
pt → Português  
es → Espanhol  
fr → Francês

---

## 🔑 Configuração do Banco

Configure o arquivo application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Certifique-se de que o banco exista:

literalura_db

---

## 🧹 Limpeza do Banco (Testes)

Para limpar os dados durante testes execute no PostgreSQL:

TRUNCATE TABLE autor, livro RESTART IDENTITY CASCADE;

Explicação:

TRUNCATE → remove todos os registros  
RESTART IDENTITY → reinicia IDs auto-increment  
CASCADE → remove dependências entre tabelas

---

## 🌐 API Utilizada

Este projeto consome a API pública:

https://gutendex.com/books/

Documentação:

https://gutendex.com/

---

## 🧪 Teste Rápido

1. Inicie a aplicação

./mvnw spring-boot:run

2. Escolha a opção

1

3. Busque um livro

Dracula

4. Liste os livros

2

---

## 👤 Autor

João Paulo Z. Llorca

GitHub  
https://github.com/johnny-dll

LinkedIn  
https://www.linkedin.com/in/joaopaulozllorca/