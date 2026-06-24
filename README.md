# Order Management API - Backend Technical Assessment

A robust, highly cohesive, and loosely coupled RESTful API designed to manage customer profiles and orchestrate external order/item data. Built with **Java** and **Spring Boot**, this project implements **Clean Architecture (Hexagonal / Ports and Adapters)** to guarantee complete isolation of the business logic/domain from infrastructure and framework details.

## 🏗️ Architectural Highlights

* **Clean Architecture & Dependency Rule:** The business domain (`model`, `usecase`) has zero dependencies on external frameworks, databases, or HTTP clients. 
* **Hexagonal Design (Ports and Adapters):** MongoDB and OpenFeign are implemented as interchangeable outbound adapters.
* **High-Performance Fuzzy Search:** The search engine uses the **Levenshtein Distance** algorithm (via Apache Commons Text) to provide typo-tolerant searching. Furthermore, temporal complexity was reduced from O(N x M) to O(1) by pre-indexing external catalog item data into Hash Map before crossing relational references and before using fuzzy search.
* **Automatic Data Seeding:** The application intercepts the startup process to inject mock clients into the database, enabling immediate testing of external API data crossing.

## ⚙️ Prerequisites

* Java Development Kit (JDK) 17 or higher
* Docker & Docker Compose (for MongoDB container)
* Maven (Wrapper included in the project)

## 🚀 Running the Application Locally

Follow these steps to deploy the infrastructure, compile the project, and start the Spring Boot server.

**1. Start the MongoDB Database**
Ensure Docker is running on your machine, then execute:
```bash
docker-compose up -d
```

**2. Compile and Install Dependencies**
```bash
./mvnw clean install
```

**3. Run the Spring Boot Application**
```bash
./mvnw spring-boot:run
```

*Note: Upon successful startup, the `CommandLineRunner` will automatically seed the database with mock clients whose `userId`s map exactly to the external Mock API, making the application ready to test immediately.*

## 📖 API Documentation & Testing

**Swagger UI / OpenAPI 3.0**
Once the application is running, the interactive API documentation will be available at:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

**Postman Collection**
For convenience, a Postman collection containing the full CRUD lifecycle and Fuzzy Search use cases is included in the repository.

## 🧪 Running Unit Tests

The project includes comprehensive unit testing using **JUnit 5** and **Mockito** to validate core business logic and use cases without requiring infrastructure context.

To execute the test suite, run:
```bash
./mvnw clean test
```