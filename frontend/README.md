# Gerenciamento de Cursos — Front-end

Interface web para gerenciamento de cursos, desenvolvida com Spring Boot, Thymeleaf e Tailwind CSS, integrada à API REST de gerenciamento de cursos.

O sistema possui autenticação com Spring Security e utiliza JWT para comunicação segura com a API. O token é armazenado na sessão HTTP durante a utilização da aplicação.

## Stack

* Java 25
* Spring Boot 4.1.1
* Spring Web
* Thymeleaf
* Spring Security
* RestClient
* Tailwind CSS v4
* JUnit 5
* Mockito
* MockMvc

## Pré-requisitos

* Java 25
* Maven
* Node.js
* API Backend do Gerenciamento de Cursos

## Configuração

1. Instale as dependências do Tailwind CSS:

```bash
npm install
```

2. Gere o CSS do Tailwind:

```bash
npm run build:css
```

Durante o desenvolvimento, também é possível utilizar o modo `watch` para atualizar o CSS automaticamente.

3. Confira as configurações em `src/main/resources/application.properties`:

```properties
server.port=8084
api.base-url=http://localhost:8083
```

4. Suba primeiro a API Backend na porta `8083`.

5. Rode a aplicação Front-end:

```bash
mvn spring-boot:run
```

O Front-end ficará disponível em:

```text
http://localhost:8084
```

## Login

O login utiliza as mesmas credenciais cadastradas na API Backend.

| Username | Senha    |
| -------- | -------- |
| admin    | admin123 |

Após a autenticação, o JWT fornecido pela API é armazenado na sessão HTTP para realizar as operações protegidas.

## Funcionalidades

* Login com Spring Security
* Autenticação integrada com a API através de JWT
* Armazenamento do token na sessão HTTP
* Listagem de cursos
* Visualização dos detalhes de um curso
* Cadastro de novos cursos
* Validação dos campos do formulário
* Edição de cursos
* Exclusão de cursos
* Tratamento de erros
* Redirecionamento para login quando a sessão expira
* Redirecionamento para a listagem quando um curso não é encontrado
* Interface responsiva utilizando Tailwind CSS

## Integração com a API

A aplicação utiliza o `RestClient` do Spring para realizar as requisições à API Backend.

A comunicação é realizada através dos seguintes endpoints:

| Verbo  | Rota               | Descrição                         |
| ------ | ------------------ | --------------------------------- |
| POST   | `/api/auth/login`  | Autentica o usuário e obtém o JWT |
| GET    | `/api/cursos`      | Lista todos os cursos             |
| GET    | `/api/cursos/{id}` | Exibe os detalhes de um curso     |
| POST   | `/api/cursos`      | Cria um novo curso                |
| PUT    | `/api/cursos/{id}` | Atualiza um curso                 |
| DELETE | `/api/cursos/{id}` | Exclui um curso                   |

## Estrutura do projeto

```text
src/main/java/com/front_gerenciamento/
├── config/
│   ├── RestClientConfig
│   ├── SecurityConfig
│   └── GlobalWebExceptionHandler
│
├── controller/
│   ├── CursoWebController
│   └── AuthController
│
├── service/
│   ├── CursoClientService
│   ├── AuthClientService
│   └── TokenSessionService
│
├── auth/
│   └── gerenciamento da autenticação e sessão
│
└── dto/
    ├── CursoRequestDTO
    ├── CursoResponseDTO
    ├── CursoFormDTO
    ├── LoginRequestDTO
    └── LoginResponseDTO

src/main/resources/
├── templates/
│   ├── login.html
│   ├── cursos/
│   │   ├── lista.html
│   │   ├── detalhe.html
│   │   └── form.html
│   └── erro.html
│
└── static/
    └── css/
        └── output.css

src-tailwind/
└── input.css
```

## Testes

Os testes automatizados podem ser executados através do Maven:

```bash
mvn test
```

Os testes utilizam:

* JUnit 5
* Mockito
* MockMvc
* `@WebMvcTest`
* `@MockitoBean`

Os testes da camada web utilizam mocks para os serviços `CursoClientService` e `TokenSessionService`, não sendo necessário executar a API Backend para realizar os testes.

## Design

A interface foi desenvolvida com base no protótipo do desafio, utilizando uma identidade visual moderna e responsiva.

O projeto utiliza a fonte **Archivo** e uma paleta de cores personalizada configurada no Tailwind CSS.

As configurações de tema estão definidas em:

```text
src-tailwind/input.css
```

O arquivo `output.css` é gerado automaticamente pelo Tailwind CSS e não deve ser versionado no Git.

## Projeto relacionado

Este Front-end faz parte do projeto **Gerenciamento de Cursos**, sendo responsável pela interface web e consumindo a API REST desenvolvida no Backend.

**Backend:** Gerenciamento de Cursos — API REST com Spring Boot, PostgreSQL e JWT.
