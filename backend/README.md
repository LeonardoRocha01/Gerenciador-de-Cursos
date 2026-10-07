# Gerenciamento de Cursos — API (Backend)

API REST para gerenciamento de cursos, com autenticação JWT via Spring Security.
Construída como parte de um desafio em duas fases: esta é a fase 1 (backend); a fase 2 será
um front-end em Spring Boot + Thymeleaf + Tailwind.

## Stack

- Java 25
- Spring Boot 4.1.0
- Spring Web, Spring Data JPA, Spring Security
- PostgreSQL
- JWT
- Lombok

## Pré-requisitos

- Java 25 
- Maven
- PostgreSQL

## Configuração

1. Crie o banco de dados no seu Postgres:
   ```sql
   CREATE DATABASE cursos_db;
   ```

2. Copie o arquivo de exemplo de configuração:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

3. Defina as variáveis de ambiente necessárias (ajuste os valores):
   ```bash
   export DB_PASSWORD=sua_senha_do_postgres
   export JWT_SECRET=uma-string-bem-longa-e-aleatoria-para-assinar-os-tokens
   ```
4. Rode a aplicação:
   ```bash
   mvn spring-boot:run
   ```
   A API sobe em `http://localhost:8083`.

## Criando o primeiro usuário

Ainda não há endpoint de registro público. Insira um usuário diretamente no banco:

```sql
INSERT INTO usuarios (id, username, name, password, role)
VALUES (
    gen_random_uuid(),
    'admin',
    'Administrador',
    -- hash BCrypt da senha desejada (gere o seu, não reutilize este em produção)
    '$2b$10$GNf8VR/babM2LPZhPP/Vg.jzCYIDnvI6T.9gdVc79RnbCqVN8B1kK',
    'ADMIN'
);
```
(o hash acima corresponde à senha `admin123`, usada apenas em desenvolvimento local)

## Autenticação

1. `POST /api/auth/login` com `username`/`password` → devolve um JWT.
2. Envie esse token em toda requisição protegida, no header:
   ```
   Authorization: Bearer <token>
   ```
3. O token expira em 1 hora (configurável via `app.jwt.expiration-ms`).

### Exemplo com curl

```bash
curl -X POST http://localhost:8083/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

## Endpoints

Todos, exceto `/api/auth/login`, exigem `Authorization: Bearer <token>`.

| Verbo  | Rota                | Descrição             | Corpo (JSON)                                                          |
|--------|---------------------|------------------------|------------------------------------------------------------------------|
| POST   | `/api/auth/login`   | Autentica e devolve JWT| `{ "username", "password" }`                                            |
| GET    | `/api/cursos`       | Lista todos os cursos | —                                                                       |
| GET    | `/api/cursos/{id}`  | Detalha um curso      | —                                                                       |
| POST   | `/api/cursos`       | Cria um curso          | `{ "nome", "descricao", "cargaHoraria", "professor", "categoria" }`     |
| PUT    | `/api/cursos/{id}`  | Atualiza um curso     | mesmo formato do POST                                                  |
| DELETE | `/api/cursos/{id}`  | Exclui um curso        | —                                                                       |

## Estrutura do projeto

```
src/main/java/br/com/gerenciamento_cursos/
├── entity/       → CursoEntity, UsuarioEntity
├── repository/   → CursoRepository, UsuarioRepository
├── service/      → CursoService
├── controller/   → CursoController, AuthController
├── DTO/          → CursoRequestDTO, CursoResponseDTO, LoginRequestDTO, LoginResponseDTO
├── exceptions/   → CursoNotFoundException, ApiErrorResponse, GlobalExceptionHandler
└── security/     → SecurityConfig, JwtService, JwtAuthFilter, CustomUserDetailsService
```

## Erros

As respostas de erro seguem um formato padronizado:
```json
{
  "timestamp": "2026-08-19T23:52:52.128Z",
  "status": 404,
  "error": "Not Found",
  "message": "Curso não encontrado com o id: ..."
}
```

| Situação                         | Status |
|-----------------------------------|--------|
| Curso não encontrado              | 404    |
| Erro de validação (`@Valid`)      | 400    |
| Credenciais inválidas no login    | 401    |
| Sem token / token inválido        | 401    |
| Erro inesperado                   | 500    |

## Próximos passos

- Endpoint de registro de usuário.
- Testes automatizados (JUnit + MockMvc).
- Front-end em Spring Boot + Thymeleaf + Tailwind consumindo esta API.