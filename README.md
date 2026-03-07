# 📚 LiterAlura API

## 📋 Descrição do Projeto

Catálogo de livros funcional integrado à API Gutendex, desenvolvido como parte do programa Oracle Next Education (ONE). O projeto foca no consumo de APIs REST, processamento de JSON e persistência de dados relacionais para gestão de bibliotecas.

## 🛠️ Tech Stack

- **Language:** Java 17 (LTS) ☕
- **Framework:** Spring Boot 3.2.3
- **Persistence:** Spring Data JPA (Hibernate)
- **Database:** PostgreSQL 16
- **JSON Processing:** Jackson
- **Build Tool:** Maven
- **Version Control:** Git
- **Repository:** GitHub

## 🚀 Como Rodar Localmente

Clone o repositório:

git clone https://github.com/johnny-dll/Alure_ONE_G9_Trilha_Java_Spring_Framework_-_Challenge_01_de_02.git

Acesse a pasta do projeto:

cd literalura

Configure o banco de dados:  
Ajuste o arquivo `src/main/resources/application.properties` com suas credenciais do PostgreSQL.

Execute a aplicação:

./mvnw spring-boot:run

> ⚠️ **Observação:** garanta que o profile `local` esteja ativo para usar o banco de desenvolvimento.

## 💻 Menu Interativo (Console)

A interação ocorre diretamente via terminal através de um menu intuitivo:

[1] | Buscar livro pelo título: Consome a API Gutendex e persiste no banco.  
[2] | Listar livros: Exibe todos os registros salvos localmente.  
[3] | Listar autores: Mostra todos os autores cadastrados no banco.  
[4] | Autores vivos em determinado ano: Realiza um filtro temporal de autores.  
[5] | Listar livros por idioma: Filtro por siglas (PT, EN, FR, ES).

## 🔑 Configurações (Environment Variables)

Para rodar este projeto, configure as seguintes propriedades no `application.properties`:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db  
spring.datasource.username=seu_usuario  
spring.datasource.password=sua_senha  
spring.profiles.active=local

> ⚠️ **Dica:** garanta que o banco `literalura_db` esteja criado e acessível localmente antes de iniciar a aplicação.

## 🧪 Testes Locais

1. Inicie a aplicação com `./mvnw spring-boot:run`.
2. No menu interativo, selecione `[1]` e busque um livro conhecido.
3. Verifique se o livro foi salvo no banco e se aparece na listagem `[2]`.
4. Teste listagem de autores `[3]` e filtros `[4]` e `[5]` para garantir integridade dos dados.
5. Observe os logs no console para confirmar conexão com o PostgreSQL e operações Hibernate sem erros.

## 👤 Autor

João Paulo Z. Llorca

- [GitHub](https://github.com/johnny-dll)
- [LinkedIn](https://www.linkedin.com/in/joaopaulozllorca/)