# Blog API

API REST para gerenciamento de posts, desenvolvida com **Java 25**, **Spring Boot 4.1.1**, **Spring Data JPA** e **MariaDB**.

## Tecnologias

* Java 25
* Spring Boot 4.1.1
* Spring MVC
* Spring Data JPA
* Spring Validation
* MariaDB 11.8
* MapStruct 1.6.3
* Swagger / OpenAPI
* JUnit 6
* Mockito 5.20.0
* Maven
* Docker
* Docker Compose
* Postman
* Newman

## Endpoints

| Método | Endpoint      | Descrição             |
| ------ | ------------- | --------------------- |
| GET    | `/posts`      | Lista todos os posts  |
| GET    | `/posts/{id}` | Busca um post pelo ID |
| POST   | `/posts`      | Cria um novo post     |
| PUT    | `/posts/{id}` | Atualiza um post      |
| DELETE | `/posts/{id}` | Remove um post        |

## Swagger

Com a aplicação em execução:

`http://localhost:8080/swagger-ui/index.html`

## Banco de Dados

A aplicação utiliza **MariaDB 11.8**.

### Docker — MariaDB

Criar o volume:

```bash
docker volume create mariadb_data
```

Criar o container:

```bash
docker run -d \
  --name meu_mariadb \
  -p 3306:3306 \
  -e MARIADB_ROOT_PASSWORD=root \
  -v mariadb_data:/var/lib/mysql \
  --restart unless-stopped \
  mariadb:11.8
```

Criar o banco:

```bash
docker exec -it meu_mariadb mariadb -u root -proot -e "CREATE DATABASE IF NOT EXISTS blogappdb;"
```

Acessar o MariaDB:

```bash
docker exec -it meu_mariadb mariadb -u root -proot
```

## Docker Compose

O projeto também possui um `docker-compose.yml` para executar a API e o MariaDB juntos.

```bash
docker compose up -d
```

O Compose configura:

* MariaDB 11.8
* Banco `blogappdb`
* Volume persistente `mariadb_data`
* API na porta `8080`
* MariaDB na porta `3306`
* Healthcheck do MariaDB
* Inicialização da API somente após o MariaDB estar saudável

Acesse:

`http://localhost:8080`

Swagger:

`http://localhost:8080/swagger-ui/index.html`

Para parar os containers:

```bash
docker compose down
```

## Configuração Local

Quando a API é executada diretamente pelo Maven/IDE, utilizando o MariaDB em Docker, a conexão utiliza `localhost`:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/blogappdb?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=root
```

Quando a API é executada pelo Docker Compose, a conexão utiliza o nome do serviço `mariadb`:

```text
jdbc:mariadb://mariadb:3306/blogappdb
```

## Postman

O projeto possui uma coleção do Postman para testar os endpoints e as validações da API.

A coleção verifica:

* GET de todos os posts
* GET de post por ID
* POST de novo post
* PUT de post
* DELETE de post
* ID inexistente
* Validação do autor
* Validação do título
* Validação da data
* Validação de data futura
* Validação do texto
* Estrutura das respostas de erro do `GlobalExceptionHandler`

Arquivo:

`blog-api.postman_collection.json`

### Newman

Instalar o Newman:

```bash
npm install -g newman --silent
```

Executar a coleção:

```bash
newman run blog-api.postman_collection.json
```

A API deve estar disponível em:

```text
http://localhost:8080
```

## Testes

Executar os testes Maven:

```bash
mvn test
```

Gerar o projeto:

```bash
mvn clean package
```

## Executar Localmente

Com o MariaDB disponível:

```bash
mvn spring-boot:run
```

Ou execute a classe principal da aplicação pela IDE.

## Executar com Docker Compose

Para executar a aplicação completa:

```bash
docker compose up -d
```

Verificar os containers:

```bash
docker compose ps
```

Parar a aplicação:

```bash
docker compose down
```

## Projeto

**Blog API** — API REST desenvolvida para estudos de desenvolvimento backend com Java e Spring Boot.

**Autor:** Loester Botelho
