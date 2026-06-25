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

Follow these steps to set up the environment variables, deploy the infrastructure, compile the project, and start the Spring Boot server.

**1. Configure Environment Variables**
Copy the template environment file and rename it to `.env` in the root directory to establish the required configuration properties:
```bash
cp .env.example .env

**2. Start the MongoDB Database**
Ensure Docker is running on your machine, then execute:
```bash
docker-compose up -d
```

**3. Compile and Install Dependencies**
```bash
./mvnw clean install
```

**4. Run the Spring Boot Application**
```bash
./mvnw spring-boot:run
```

*Note: Upon successful startup, the `CommandLineRunner` will automatically seed the database with mock clients whose `userId`s map exactly to the external Mock API, making the application ready to test immediately.*

## 📖 API Documentation & Testing

**Swagger UI / OpenAPI 3.0 (Local & Cloud Environments ☁️)**
For cloud, the interactive API documentation will be available at:
👉 **[https://order-management-0dsd.onrender.com/swagger-ui/index.html](https://order-management-0dsd.onrender.com/swagger-ui/index.html)**

In local, once the application is running, the interactive API documentation will be available at:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

**Postman Collections (Local & Cloud Environments ☁️)**
For convenience, the `postman/` directory includes two collections containing the full CRUD lifecycle and all Fuzzy Search use cases, tailored for different environments:

* **`Ecommerce-order-management-Local.postman_collection.json`**: Pre-configured to test the API locally (`localhost:8080`).
* **`Ecommerce-order-management.postman_collection_cloud.json`**: Pre-configured to point directly to the live production environment hosted on Render.

Evaluators can import these collections to test the endpoints seamlessly, whether running the infrastructure on their own machine or interacting directly with the deployed application.

## 🧪 Running Unit Tests

The project includes comprehensive unit testing using **JUnit 5** and **Mockito** to validate core business logic and use cases without requiring infrastructure context.

To execute the test suite, run:
```bash
./mvnw clean test
```