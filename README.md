# tdsoft_registration


## Descrição do Projeto
É um projeto em backend de Gerenciador de Clientes, desenvolvido utilizando Java (Spring Boot) e PostgreSQL. A API RESTful permite operações de criação, leitura, atualização e exclusão (CRUD) de clientes.

## Funcionalidades do Usuario
- Cadastro de usuário.
- Login de usuário.
- Atualização de dados do usuário.
- Integração com banco de dados PostgreSQL.

## Funcionalidades do Usuario com Cliente, relacionamento de 1:N
- Cadastrar um cliente.
- Listagem de todos os clientes.
- Listagem de clientes por usuário.
- Atualização de dados do cliente.
- Deletar cliente.
- Integração com banco de dados PostgreSQL.


## Tecnologias Utilizadas
- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Lombok](https://projectlombok.org/)
- [Maven](https://maven.apache.org/)


## Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior
- PostgreSQL instalado e em execução

## Configuração do Banco de Dados
Certifique-se de que o PostgreSQL esteja em execução e crie um banco de dados chamado `registration`. Atualize as credenciais de acesso no arquivo `env.Example`, quando atualizar as variaveis de ambiente, renomear o arquivo para `env`.


## Como Executar o Projeto

1. ### Instalação das dependências:
   
   ```bash
    mvn clean install
   ```
   
2. ### Execução da aplicação:
   ```bash
   mvn spring-boot:run
   ```


    A API estará disponível em [http://localhost:8080](http://localhost:8080).
  

## Documentação do projeto

- Link com a documentação completa do projeto [Documentação](http://localhost:8080).

## Como executar os testes


