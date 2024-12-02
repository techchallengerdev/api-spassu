# Desafio Técnico Spassu (Desenvolvedor Full stack Master)

1. Criar um projeto utilizando as boas práticas de mercado e apresentar o mesmo demonstrando o passo a passo de sua criação 
(base de dados, tecnologias, aplicação, metodologias, frameworks, etc).

# Projeto

O projeto consiste em um cadastro de livros.
No final deste documento foi disponibilizado um modelo dos dados.

# Tecnologia

1. A tecnologia a ser utilizada é sempre Web e referente a vaga em que está concorrendo.

2. A implementação do projeto ficará por sua total responsabilidade assim como os componentes a serem utilizados (relatórios, 
camada de persistência, etc) com algumas premissas

3. O banco de dados é o de sua preferência. A utilização de camada de persistência também é escolha sua.

Um projeto de demonstração demonstrando como usar o Swagger (baseado na especificação OpenAPI 3.0)
com Spring Boot para documentar RestAPI.

Tech stack backend:
- Maven 3.3.2
- Java 19
- Spring Boot 3.2.0

# Running the application

## Running locally

1. Clone the repository:

`git clone https://github.com/your-username/biblioteca-api.git`

2. Navigate to the project directory:

`cd api-backend-spassu`

3. Compile and run the application:

`./mvnw spring-boot:run`

The application will be available at http://localhost:8081.

## Running with Docker

1. Build the Docker image:

`docker build -t api-backend-spassu .`

2. Run the container:

`docker run -p 8081:8081 biblioteca-api`

The application will be available at http://localhost:8081.

## Tests

To run the tests, use the following command:

`./mvnw test`

The test coverage reports will be generated in the target/site/jacoco directory.

## Endpoints

You can test the API endpoints using cURL. Here are some examples:

## List all books

`curl http://localhost:8080/api/v1/livros`

## Create a new book

`curl -X POST -H "Content-Type: application/json" -d '{"titulo":"Livro Teste", "editora":"Editora Teste", "edicao":1, "anoPublicacao":"2023", "autorCodAus":[1,2], "assuntoCodAss":[3,4]}' http://localhost:8081/api/v1/livros`

## Retrieve a book by ID

`curl http://localhost:8081/api/v1/livros/1`

## Update an existing book

`curl -X PUT -H "Content-Type: application/json" -d '{"codigo":1, "titulo":"Livro Teste Atualizado", "editora":"Editora Teste Atualizada", "edicao":2, "anoPublicacao":"2024", "autorCodAus":[1,2], "assuntoCodAss":[3,4]}' http://localhost:8081/api/v1/livros/1`

## Delete a book

`curl -X DELETE http://localhost:8081/api/v1/livros/1`

The complete API documentation, including all endpoints, can be accessed at http://localhost:8081/swagger-ui.html.