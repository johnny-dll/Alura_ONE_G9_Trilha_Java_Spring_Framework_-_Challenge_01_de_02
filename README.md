# 📚 LiterAlura API


## 📋 Descrição do Projeto

Catálogo de livros funcional integrado à API Gutendex, desenvolvido como parte do programa Oracle Next Education (ONE). O projeto foca no consumo de APIs REST, processamento de JSON e persistência de dados relacionais para gestão de bibliotecas.


## 🛠️ Tech Stack

Language: Java 17 (LTS) ☕

Framework: Spring Boot 3.2.3

Persistence: Spring Data JPA (Hibernate)

Database: PostgreSQL 16

JSON Processing: Jackson

Build Tool: Maven

Version Control: Git

Repository: GitHub


## 🚀 Como Rodar Localmente

Clone o repositório:

git clone https://github.com/johnny-dll/literalura.git

Acesse a pasta do projeto:

cd literalura

Configure o banco de dados:
Ajuste o arquivo src/main/resources/application.properties com suas credenciais do PostgreSQL.

Execute a aplicação:

./mvnw spring-boot:run


## 💻 Menu Interativo (Console)

A interação ocorre diretamente via terminal através de um menu intuitivo:

[1] | Buscar livro pelo título: Consome a API Gutendex e persiste no banco.

[2] | Listar livros: Exibe todos os registros salvos localmente.

[3] | Listar autores: Mostra todos os autores cadastrados no banco.

[4] | Autores vivos em determinado ano: Realiza um filtro temporal de autores.

[5] | Listar livros por idioma: Filtro por siglas (PT, EN, FR, ES).


## 🔑 Configurações (Environment Variables)

Para rodar este projeto, você precisará configurar as seguintes propriedades no seu application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha


## 👤 Autor

João Paulo Z. Llorca

- [@GitHub](https://github.com/johnny-dll)
- [@LinkedIn](https://www.linkedin.com/in/joaopaulozllorca/)