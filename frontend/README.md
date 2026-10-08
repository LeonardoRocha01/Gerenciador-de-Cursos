Gerenciamento de Cursos — Front-end

Interface web (Spring Boot + Thymeleaf + Tailwind CSS) para gerenciamento de cursos, consumindo a API REST construída na fase 1 do desafio. Login local com Spring Security, token JWT da API guardado na sessão HTTP.

Stack
Java 25
Spring Boot 4.1.1
Spring Web, Thymeleaf, Spring Security
RestClient para consumo da API
Tailwind CSS v4 (via Tailwind CLI)
JUnit 5 + Mockito + MockMvc (testes de Controller)
Pré-requisitos
Java 25 (ou compatível com o pom.xml)
Maven
Node.js (para compilar o CSS do Tailwind)
A API backend rodando em http://localhost:8083 (ver repositório da API)
Configuração
Instale as dependências do Tailwind:
bash
   npm install
Gere o CSS (uma vez, ou use --watch durante o desenvolvimento):
bash
   npm run build:css
Confira src/main/resources/application.properties:
properties
   server.port=8084
   api.base-url=http://localhost:8083
Suba a API backend primeiro (porta 8083), depois rode esta aplicação:
bash
   mvn spring-boot:run

O front-end sobe em http://localhost:8084.

Login

Use as mesmas credenciais cadastradas na API:

username	senha
admin	admin123
Funcionalidades
Login (autentica na API, guarda o JWT na sessão HTTP)
Listagem de cursos
Detalhes de um curso
Criar novo curso (com validação de campos)
Editar curso existente (mesmo formulário de criação)
Excluir curso
Tratamento de erros: sessão expirada redireciona para o login com uma mensagem; curso não encontrado redireciona para a listagem
Estrutura do projeto
src/main/java/com/front_gerenciamento/
├── config/       → RestClientConfig, SecurityConfig, GlobalWebExceptionHandler
├── controller/   → CursoWebController, AuthController
├── service/      → CursoClientService (chama a API via RestClient)
├── auth/         → AuthClientService, TokenSessionService
└── dto/          → CursoRequestDTO, CursoResponseDTO, CursoFormDTO, LoginRequestDTO, LoginResponseDTO

src/main/resources/
├── templates/    → login.html, cursos/lista.html, cursos/detalhe.html, cursos/form.html, c/css/   → output.css (gerado pelo Tailwind, não versionado)

src-tailwind/
└── input.css     → ponto de entrada do Tailwind (cores, fonte, diretivas @source)
Rodando os testes
bash
mvn test

Os testes usam @WebMvcTest (carregam só a camada web) com CursoClientService e TokenSessionService simulados via @MockitoBean — não é necessário ter a API rodando para rodar os testes.