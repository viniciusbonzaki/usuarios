# API de Usuários — Agendador de Tarefas

## 📌 Sobre o Projeto


O Agendador de Tarefas tem como objetivo permitir que usuários cadastrem tarefas com datas e horários específicos e recebam notificações por e-mail quando o momento programado se aproximar.

Para organizar as responsabilidades da aplicação, o sistema foi desenvolvido com base na **arquitetura de microserviços**, dividindo suas funcionalidades em serviços independentes e especializados.

O ecossistema é composto por quatro microserviços:

- **API de Usuários:** responsável pelo cadastro, consulta, atualização e exclusão de usuários, incluindo o gerenciamento de seus endereços e telefones.
- **API de Tarefas:** responsável pelo gerenciamento das tarefas cadastradas na aplicação.
- **API de Notificação:** responsável pelo processamento e envio de notificações por e-mail.
- **BFF (Backend for Frontend):** responsável por integrar os demais microserviços e centralizar a comunicação com a camada cliente.


Esse repositório é dedicado exclusivamente à **API de Usuários**, um dos quatro microserviços que compõem o projeto Agendador de Tarefas que foi desenvolvido utilizando Java com Spring Boot, responsável por gerenciar as informações dos usuários e disponibilizar os recursos necessários para as operações relacionadas a seus dados pessoais.

Além das operações de gerenciamento de usuários, o microserviço conta com autenticação baseada em JWT, controle de segurança com Spring Security, persistência de dados em PostgreSQL e integração com a API externa ViaCEP para consulta de informações de endereço a partir do CEP.

## 🎯 Funcionalidades

A API de Usuários disponibiliza as seguintes funcionalidades:

### Gerenciamento de usuários

- Cadastro de novos usuários.
- Consulta dos dados de um usuário a partir do e-mail.
- Atualização dos dados cadastrais de um usuário.
- Exclusão de um usuário a partir do e-mail.

### Gerenciamento de endereços

- Cadastro de endereço associado a um usuário.
- Atualização dos dados de endereço de um usuário.
- Consulta de informações de endereço por CEP utilizando a API ViaCEP.

### Gerenciamento de telefones

- Cadastro de telefone associado a um usuário.
- Atualização dos dados de telefone de um usuário.

### Autenticação e segurança

- Autenticação de usuários por meio de login.
- Geração e validação de tokens JWT.
- Proteção de endpoints conforme as regras definidas no Spring Security.

## 🛠️ Tecnologias Utilizadas

As principais tecnologias e ferramentas utilizadas no desenvolvimento deste microserviço são:

- **Java 17**: Linguagem de programação
- **Spring Boot**: Desenvolvimento e configuração da aplicação backend
- **Spring Security**: Implementação dos mecanismos de segurança
- **JWT**: Autenticação baseada em tokens
- **Gradle**: Gerenciamento de dependências e build
- **PostgreSQL**: Banco de dados relacional
- **Docker**: Containerização da aplicação
- **ViaCEP API**: Consulta de informações de endereço a partir do CEP

## 📂 Arquitetura e Organização do Projeto

O projeto utiliza uma organização em camadas para separar as responsabilidades da aplicação, facilitar a manutenção do código e promover a separação entre regras de negócio, infraestrutura e exposição dos endpoints.

### Business

Camada responsável por concentrar a lógica de negócio e os componentes utilizados no processamento das operações da aplicação.

- `UsuarioService`: implementa as regras de negócio relacionadas aos usuários.
- `ViaCepService`: coordena a consulta de informações de endereço por meio da integração com o ViaCEP.
- `Converter`: auxilia na conversão entre objetos utilizados pelas diferentes camadas.
- `DTOs`: objetos utilizados para transportar dados entre as camadas e nas operações da API.

### Infrastructure

Camada responsável pelos componentes de persistência, acesso a recursos externos e infraestrutura técnica da aplicação.

- `Entities`: representam as entidades persistidas no banco de dados.
- `Repositories`: abstraem as operações de acesso e persistência de dados.
- `Exceptions`: agrupam as exceções específicas utilizadas pela aplicação.
- `Client`: realiza a comunicação com a API externa.
- `Security`: implementam e configuram os mecanismos de autenticação e segurança.

### Controller

Camada responsável por expor os recursos HTTP e receber as requisições dos clientes.

- `UsuarioController`: disponibiliza os endpoints relacionados ao gerenciamento de usuários, telefones, endereços e autenticação.
- `GlobalExceptionHandler`: centraliza o tratamento de exceções e a construção das respostas de erro da API.

## 🗃️ Modelagem de Dados

As principais entidades do domínio são:

- **Usuario**: representa o usuário cadastrado na aplicação e reúne as informações necessárias para seu gerenciamento e autenticação.
- **Endereco**: representa os dados de endereço associados a um usuário. A aplicação também disponibiliza uma integração com o ViaCEP para consultar informações a partir do CEP.
- **Telefone**: representa os dados de telefone associados a um usuário.

A estrutura do domínio contempla a associação entre usuário, endereço e telefone, permitindo que essas informações sejam gerenciadas pelas operações disponibilizadas pela API.

## 🔐 Autenticação e Segurança

A aplicação utiliza **Spring Security** em conjunto com **JSON Web Token (JWT)** para implementar o mecanismo de autenticação.

Essa abordagem permite autenticar usuários e validar tokens nas requisições protegidas, de acordo com as regras configuradas para a aplicação.

- **`JwtRequestFilter`** Intercepta requisições para processar e validar o token JWT, conforme a implementação.
- **`JwtUtil`** Centraliza operações relacionadas à criação e validação de tokens JWT.
- **`SecurityConfig`** Define as configurações de segurança e as regras de acesso aos endpoints.
- **`UserDetailsServiceImpl`** Implementa o carregamento dos dados do usuário para integração com o Spring Security.

A autenticação é realizada por meio do endpoint de login, que permite ao usuário obter um token para acessar os recursos protegidos, conforme a configuração da aplicação.

## ⚠️ Tratamento de Exceções

O projeto possui uma classe `GlobalExceptionHandler`, responsável por centralizar o tratamento das exceções geradas durante o processamento das requisições.

Essa abordagem permite padronizar as respostas de erro da API e separar o tratamento de falhas da lógica dos controladores.

## 🌐 Endpoints da API

Esta seção apresenta as operações disponibilizadas pela API de Usuários.

Os caminhos exatos devem ser confirmados na implementação do `UsuarioController`.

| Método HTTP | Recurso | Descrição |
|---|---|---|
| POST | Usuário | Cadastrar um novo usuário. |
| POST | Login | Autenticar um usuário. |
| GET | Usuário | Buscar os dados de um usuário pelo e-mail. |
| PUT | Usuário | Atualizar os dados cadastrais de um usuário. |
| DELETE | Usuário | Excluir um usuário pelo e-mail. |
| POST | Endereço | Adicionar um endereço a um usuário. |
| PUT | Endereço | Atualizar o endereço de um usuário. |
| GET | ViaCEP | Consultar dados de endereço a partir de um CEP. |
| POST | Telefone | Adicionar um telefone a um usuário. |
| PUT | Telefone | Atualizar o telefone de um usuário. |

### Exemplos de utilização

Os exemplos completos de requisições podem ser documentados com os seguintes dados:

- **Cadastro de usuário:** JSON com os dados exigidos pela aplicação.
- **Login:** credenciais necessárias para autenticação.
- **Consulta de usuário:** e-mail utilizado como parâmetro de busca.
- **Consulta ViaCEP:** CEP utilizado na pesquisa de endereço.
- **Atualização de dados:** identificador ou e-mail do usuário e os campos que podem ser alterados.

Para cada endpoint, recomenda-se documentar a rota exata, os parâmetros, o corpo JSON, o código HTTP de sucesso e os possíveis erros.

## 🌐 Integração com a API ViaCEP

A API de Usuários integra-se ao serviço externo [ViaCEP](https://viacep.com.br/) para consultar informações de endereço utilizando um CEP como referência.

Essa funcionalidade permite obter dados de endereço sem a necessidade de manter manualmente todas as informações de localização durante o cadastro.

O fluxo geral de integração é composto por:

1. Recebimento do CEP informado na requisição.
2. Consulta ao serviço externo ViaCEP.
3. Processamento da resposta recebida.
4. Disponibilização das informações de endereço ao cliente da API, conforme o contrato implementado.

A integração é organizada por meio do `ViaCepService` e do cliente responsável pela comunicação com o serviço externo.

## 🐳 Docker

O projeto utiliza Docker para containerização, facilitando a padronização do ambiente de execução e a implantação da aplicação.

A configuração de execução está presente nos arquivos Docker como `Dockerfile` e `docker-compose.yaml`.

Antes de executar a aplicação em containers, verifique as configurações de conexão com o PostgreSQL e os parâmetros necessários para autenticação e integração com os serviços externos.

## 📚 Conceitos Aplicados

O desenvolvimento deste microserviço envolve conceitos relevantes para a construção de aplicações backend:

- Arquitetura de microserviços.
- Desenvolvimento de APIs REST.
- Separação de responsabilidades em camadas.
- Programação com Java 17 e Spring Boot.
- Persistência de dados relacionais com PostgreSQL.
- Autenticação baseada em JWT.
- Segurança de endpoints com Spring Security.
- Integração com APIs externas.
- Tratamento global de exceções.
- Containerização com Docker.
- Gerenciamento de dependências e build com Gradle.
