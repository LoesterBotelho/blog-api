# Blog API

REST API para gerenciamento de posts, desenvolvida com Java 25 e Spring Boot 4.1.1.

O projeto utiliza arquitetura em camadas, DTOs, validação de dados, tratamento global de exceções, persistência com JPA, mapeamento com MapStruct, testes automatizados e execução utilizando Docker e Docker Compose.

## Tecnologias

- Java 25
- Spring Boot 4.1.1
- Spring MVC
- Spring Data JPA
- Jakarta Bean Validation
- MariaDB 11.8
- MapStruct 1.6.3
- Swagger / OpenAPI
- JUnit 6
- Mockito 5.20.0
- Maven
- Docker
- Docker Compose
- Postman
- Newman

## Funcionalidades

- Criar posts
- Listar posts
- Buscar post por ID
- Atualizar posts
- Excluir posts
- Validar dados de entrada
- Tratamento global de exceções
- Documentação da API com Swagger/OpenAPI
- Testes da camada Controller
- Testes da camada Service
- Testes automatizados da API com Postman e Newman
- Execução da aplicação e banco de dados com Docker Compose

## Arquitetura

O projeto utiliza uma arquitetura em camadas com separação de responsabilidades.

```text
Client
  |
  v
Controller
  |
  v
Service
  |
  v
Repository
  |
  v
MariaDB
```

Fluxo de entrada:

```text
Request
   |
   v
PostRequestDto
   |
   v
Controller
   |
   v
Service
   |
   v
MapStruct
   |
   v
PostModel
   |
   v
Repository
   |
   v
MariaDB
```

Fluxo de resposta:

```text
MariaDB
   |
   v
Repository
   |
   v
PostModel
   |
   v
MapStruct
   |
   v
PostResponseDto
   |
   v
Controller
   |
   v
Response
```

## Estrutura do projeto

```text
src
├── main
│   ├── java
│   │   └── com.api.blog_api
│   │       ├── controller
│   │       │   └── PostController.java
│   │       │
│   │       ├── service
│   │       │   ├── PostService.java
│   │       │   └── PostServiceImpl.java
│   │       │
│   │       ├── repository
│   │       │   └── PostRepository.java
│   │       │
│   │       ├── dto
│   │       │   ├── request
│   │       │   │   └── PostRequestDto.java
│   │       │   │
│   │       │   └── response
│   │       │       ├── PostResponseDto.java
│   │       │       └── ErroResponseDto.java
│   │       │
│   │       ├── mapper
│   │       │   └── PostMapper.java
│   │       │
│   │       ├── model
│   │       │   └── PostModel.java
│   │       │
│   │       └── exception
│   │           ├── GlobalExceptionHandler.java
│   │           ├── RegistroNaoEncontradoException.java
│   │           └── ErroCampoResponse.java
│   │
│   └── resources
│       └── application.properties
│
└── test
    └── java
        └── com.api.blog_api
            ├── controller
            │   └── PostControllerTest.java
            │
            └── service
                └── PostServiceTest.java
```

## Modelo de dados

Um post possui os seguintes atributos:

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `UUID` | Identificador único |
| `autor` | `String` | Autor do post |
| `data` | `LocalDate` | Data do post |
| `titulo` | `String` | Título do post |
| `texto` | `String` | Conteúdo do post |

## DTOs

O projeto utiliza Data Transfer Objects para separar os dados de entrada e saída da API dos objetos utilizados internamente pela aplicação.

### PostRequestDto

Utilizado nas operações de criação e atualização.

```json
{
    "autor": "Loester Botelho",
    "data": "2026-09-18",
    "titulo": "Aprendendo Spring Boot",
    "texto": "Estudando desenvolvimento de APIs REST com Spring Boot."
}
```

### PostResponseDto

Utilizado nas respostas da API.

```json
{
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "autor": "Loester Botelho",
    "data": "2026-09-18",
    "titulo": "Aprendendo Spring Boot",
    "texto": "Estudando desenvolvimento de APIs REST com Spring Boot."
}
```

## MapStruct

O MapStruct é utilizado para realizar o mapeamento entre DTOs e entidades da aplicação.

```text
PostRequestDto
      |
      v
PostModel
```

```text
PostModel
      |
      v
PostResponseDto
```

Essa abordagem mantém a lógica de conversão centralizada no mapper e evita código de conversão manual nas camadas Controller e Service.

## Validação

A aplicação utiliza Jakarta Bean Validation para validar os dados recebidos pela API.

### Autor

- Obrigatório
- Mínimo de 3 caracteres
- Máximo de 100 caracteres

```java
@NotBlank
@Size(min = 3, max = 100)
```

### Data

- Obrigatória
- Não pode ser futura

```java
@NotNull
@PastOrPresent
```

### Título

- Obrigatório
- Mínimo de 3 caracteres
- Máximo de 100 caracteres

```java
@NotBlank
@Size(min = 3, max = 100)
```

### Texto

- Obrigatório
- Mínimo de 10 caracteres

```java
@NotBlank
@Size(min = 10)
```

## Endpoints

Base URL:

```text
http://localhost:8080
```

| Método | Endpoint | Status |
|---|---|---|
| GET | `/posts` | 200 |
| GET | `/posts/{id}` | 200 |
| POST | `/posts` | 201 |
| PUT | `/posts/{id}` | 200 |
| DELETE | `/posts/{id}` | 204 |

### Listar posts

```http
GET /posts
```

Resposta:

```text
200 OK
```

Exemplo:

```json
[
    {
        "id": "550e8400-e29b-41d4-a716-446655440000",
        "autor": "Loester Botelho",
        "data": "2026-09-18",
        "titulo": "Aprendendo Spring Boot",
        "texto": "Estudando desenvolvimento de APIs REST com Spring Boot."
    }
]
```

### Buscar post por ID

```http
GET /posts/{id}
```

Exemplo:

```http
GET /posts/550e8400-e29b-41d4-a716-446655440000
```

Resposta:

```text
200 OK
```

### Criar post

```http
POST /posts
```

Request:

```json
{
    "autor": "Loester Botelho",
    "data": "2026-09-18",
    "titulo": "Aprendendo Spring Boot",
    "texto": "Estudando desenvolvimento de APIs REST com Spring Boot."
}
```

Resposta:

```text
201 Created
```

### Atualizar post

```http
PUT /posts/{id}
```

Request:

```json
{
    "autor": "Loester Botelho",
    "data": "2026-09-18",
    "titulo": "Aprendendo Spring Boot - Atualizado",
    "texto": "Estudando desenvolvimento de APIs REST com Spring Boot."
}
```

Resposta:

```text
200 OK
```

### Excluir post

```http
DELETE /posts/{id}
```

Resposta:

```text
204 No Content
```

## Tratamento global de exceções

O tratamento de exceções é centralizado na classe:

```text
GlobalExceptionHandler
```

A classe utiliza `@RestControllerAdvice` para tratar exceções relacionadas à API.

Atualmente são tratados:

- `RegistroNaoEncontradoException`
- `MethodArgumentNotValidException`

### 404 Not Found

Quando um post não é encontrado:

```json
{
    "status": 404,
    "erro": "Not Found",
    "mensagem": "Post não encontrado com o ID: ...",
    "dataHora": "2026-09-18T20:30:00"
}
```

### 400 Bad Request

Quando os dados enviados não atendem às regras de validação:

```json
{
    "status": 400,
    "erro": "Bad Request",
    "mensagem": "autor: Autor é obrigatório",
    "dataHora": "2026-09-18T20:30:00"
}
```

O tratamento atual retorna a primeira mensagem de erro de validação encontrada.

## Testes automatizados

O projeto possui testes para as camadas Controller e Service, além de testes de API utilizando Postman e Newman.

```text
Controller
    |
    +-- @WebMvcTest
    +-- MockMvc
    +-- Mockito

Service
    |
    +-- JUnit
    +-- Mockito

API
    |
    +-- Postman
    +-- Newman
```

## Testes do Controller

Classe:

```text
PostControllerTest
```

A camada HTTP utiliza:

- JUnit
- Spring Boot Test
- `@WebMvcTest`
- MockMvc
- Mockito

Principais cenários testados:

- Listagem de posts
- Busca por ID
- Criação de post
- Atualização de post
- Exclusão de post
- Autor vazio
- Autor com menos de 3 caracteres
- Título vazio
- Título com menos de 3 caracteres
- Data nula
- Data futura
- Texto vazio
- Texto com menos de 10 caracteres

Os cenários de validação esperam:

```text
400 Bad Request
```

## Testes do Service

Classe:

```text
PostServiceTest
```

A camada de negócio utiliza:

- JUnit
- Mockito
- `@Mock`
- `@InjectMocks`
- `assertEquals`
- `assertNotNull`
- `assertThrows`
- `verify`
- `never`

Principais cenários testados:

- Listagem de posts
- Busca por ID
- Criação de post
- Atualização de post
- Exclusão de post
- Post inexistente
- Atualização de post inexistente
- Exclusão de post inexistente

## Executar os testes

Executar todos os testes:

```bash
mvn test
```

Gerar o pacote da aplicação:

```bash
mvn clean package
```

Executar a aplicação:

```bash
mvn spring-boot:run
```

## Postman

O projeto possui uma coleção Postman para testar os endpoints e validar o comportamento do `GlobalExceptionHandler`.

Arquivo:

```text
blog-api.postman_collection.json
```

A coleção utiliza assertions com:

```javascript
pm.test()
pm.expect()
```

## Cenários do Postman

| # | Método | Endpoint / Cenário | Resultado |
|---|---|---|---|
| 01 | GET | Listar todos os posts | 200 |
| 02 | POST | Criar post válido | 201 |
| 03 | GET | Buscar post por ID | 200 |
| 04 | GET | Buscar post inexistente | 404 |
| 05 | PUT | Atualizar post | 200 |
| 06 | DELETE | Excluir post | 204 |
| 07 | POST | Autor vazio | 400 |
| 08 | POST | Autor inválido | 400 |
| 09 | POST | Título vazio | 400 |
| 10 | POST | Data nula | 400 |
| 11 | POST | Data futura | 400 |
| 12 | POST | Texto inválido | 400 |

A coleção utiliza a variável:

```text
postId
```

O ID retornado durante a criação do post é armazenado nessa variável e posteriormente utilizado nas requisições de consulta, atualização e exclusão.

## Newman

O Newman permite executar a coleção Postman diretamente pelo terminal.

Instalação:

```bash
npm install -g newman --silent
```

Execução:

```bash
newman run blog-api.postman_collection.json
```

## Banco de dados

O projeto utiliza MariaDB 11.8.

| Configuração | Valor |
|---|---|
| Banco | `blogappdb` |
| Usuário | `root` |
| Senha | `root` |
| Porta | `3306` |

## Executar MariaDB com Docker

Criar o volume:

```bash
docker volume create mariadb_data
```

Criar o container:

```bash
docker run -d --name meu_mariadb -p 3306:3306 -e MARIADB_ROOT_PASSWORD=root -v mariadb_data:/var/lib/mysql --restart unless-stopped mariadb:11.8
```

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

```bash
docker exec -it meu_mariadb \
  mariadb -u root -proot \
  -e "CREATE DATABASE IF NOT EXISTS blogappdb;"
```

Acessar o MariaDB:

```bash
docker exec -it meu_mariadb \
  mariadb -u root -proot
```

## Docker Compose

O projeto também pode executar a API e o banco de dados utilizando Docker Compose.

Arquitetura:

```text
Docker Compose
|
+-- blog-api
|     |
|     +-- Spring Boot :8080
|
+-- mariadb
      |
      +-- MariaDB :3306
```

O serviço da API depende da disponibilidade do banco de dados antes de iniciar.

O MariaDB utiliza volume persistente para preservar os dados.

### Iniciar os serviços

```bash
docker compose up -d
```

### Verificar os serviços

```bash
docker compose ps
```

### Visualizar logs

```bash
docker compose logs -f
```

### Parar os serviços

```bash
docker compose down
```

## Configuração local

Quando a aplicação é executada diretamente no ambiente local:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/blogappdb?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=root
```

Nesse cenário:

```text
Spring Boot
    |
    | localhost:3306
    v
MariaDB
```

## Configuração com Docker Compose

Quando a API é executada dentro do Docker Compose, o banco é acessado através do nome do serviço:

```text
mariadb
```

Exemplo:

```properties
spring.datasource.url=jdbc:mariadb://mariadb:3306/blogappdb
```

Nesse cenário:

```text
blog-api
    |
    | Docker Network
    v
mariadb
```

## Swagger / OpenAPI

A API possui documentação interativa utilizando Swagger/OpenAPI.

Com a aplicação em execução:

```text
http://localhost:8080/swagger-ui/index.html
```

O Swagger permite visualizar os endpoints disponíveis e executar requisições diretamente pelo navegador.

## Execução rápida

### 1. Clonar o projeto

```bash
git clone <URL_DO_REPOSITORIO>
```

### 2. Entrar no diretório

```bash
cd blog-api
```

### 3. Iniciar a aplicação e o banco

```bash
docker compose up -d
```

### 4. Verificar os containers

```bash
docker compose ps
```

### 5. Acessar a API

```text
http://localhost:8080
```

### 6. Acessar o Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

### 7. Executar os testes

```bash
mvn test
```

### 8. Executar os testes da API

```bash
newman run blog-api.postman_collection.json
```

## Estratégia de testes

O projeto utiliza diferentes níveis de testes para validar responsabilidades específicas.

| Camada | Ferramentas | Objetivo |
|---|---|---|
| Controller | JUnit, MockMvc, Mockito | Validar endpoints, HTTP e validações |
| Service | JUnit, Mockito | Validar regras da camada de negócio |
| API | Postman, Newman | Validar o comportamento da API via HTTP |

## Status HTTP

| Status | Significado | Utilização |
|---|---|---|
| `200 OK` | Requisição processada | GET / PUT |
| `201 Created` | Recurso criado | POST |
| `204 No Content` | Recurso excluído | DELETE |
| `400 Bad Request` | Dados inválidos | Validação |
| `404 Not Found` | Recurso não encontrado | Busca por ID |

## Princípios utilizados

O projeto aplica conceitos e práticas comuns no desenvolvimento de APIs REST com Spring:

- Separação de responsabilidades
- Arquitetura em camadas
- Dependency Injection
- DTOs
- Spring Data JPA
- Bean Validation
- Tratamento global de exceções
- MapStruct
- Testes unitários
- Testes da camada web
- Testes automatizados de API
- Docker
- Docker Compose
- OpenAPI / Swagger

## Autor

Loester Botelho

Full-Stack Developer | Java | Spring | Angular | React

## Licença

Projeto desenvolvido para fins de estudo, prática e evolução profissional em desenvolvimento de software, APIs REST e ecossistema Java/Spring.


```
newman run collection.json --reporters cli,htmlextra
```


# Executa os testes de integração com o Newman via Maven
```
mvn integration-test
```


