# Getting Started

## Overview

This project is a **Spring Boot**-based application designed to manage two main entities: **Category** and **Product**. The application follows the business and technical requirements, offering a robust back-end in Java and a front-end in **Angular**.

The application uses **PostgreSQL** as the database, where a **category** can contain **N products**. The back-end is developed using **Spring Boot**, and the front-end is implemented with **Angular**.

### Key Features:
- **Category and Product**: The project implements two **CRUDs** (Create, Read, Update, Delete) for the **Category** and **Product** entities, allowing full management of both.
- **Database**: It uses **PostgreSQL** as the relational database for data persistence.
- **Back-end**: Developed using **Spring Boot**, following best practices in software development and architecture.
- **Front-end**: The interface is built with **Angular**, providing a smooth and interactive user experience.
### Architecture and Goals:
- **Spring Boot** as the framework for back-end development, focusing on simplicity and efficiency.
- **PostgreSQL** as the relational database, where the **Product** table is associated with the **Category** table through a **many-to-one** relationship.
- **Angular** for creating a responsive and functional front-end.
- The application is designed to be easy to run locally or in any environment that supports **Docker** and **Docker Compose**, ensuring quick setup without complex dependencies.

### Additional Instructions:
- The project can be uploaded to **GitHub** for code review and evaluation.
- The candidate should adhere to the project’s business and technical requirements, creating a functional system that meets all the required features.


## Prerequisites

Before running the application, make sure you have the following installed:

- **Java 17+** (for building and running the application)
- **Docker** (for running PostgreSQL container)
- **Docker Compose** (for orchestrating the PostgreSQL container)
- **Maven** (for building the project)

## Project Setup

1. **Clone the repository** to your local machine:

    ```bash
    git clone https://your-repository-url.git
    cd your-project-directory
    ```

2. **Docker Setup**

   The application uses Docker and Docker Compose to run a PostgreSQL instance. This allows the application to connect
   to the database in a consistent and isolated environment.

    - Ensure Docker is installed and running on your machine. You can download Docker
      from [here](https://www.docker.com/get-started).
    - In the project directory, we have a `docker-compose.yml` file that defines the PostgreSQL service.

   **docker-compose.yml example**:

    ```yaml
    version: '3.8'
    services:
      postgres:
        image: postgres:13
        container_name: postgres_db
        environment:
          POSTGRES_DB: productdb
          POSTGRES_USER: user
          POSTGRES_PASSWORD: password
        ports:
          - "5432:5432"
        volumes:
          - postgres-data:/var/lib/postgresql/data
    volumes:
      postgres-data:
        driver: local
    ```

    - To start the PostgreSQL container, run the following command:

    ```bash
    docker-compose up -d
    ```

   This will pull the official PostgreSQL image, create a container, and run it in the background. The database will be
   accessible on port `5432`.

3. **Database Configuration**

   The application will attempt to connect to the PostgreSQL database at `localhost:5432` using the following
   credentials:

    - Database: `productdb`
    - User: `user`
    - Password: `password`

   If you want to change these values, you can modify the `docker-compose.yml` file and the Spring Boot application
   properties.

   The application uses Spring Data JPA to interact with the PostgreSQL database.

4. **Application Configuration**

   The application's configuration can be found in the `application.yml` or `application.properties` file, typically
   located in `src/main/resources/`. The relevant properties for the database connection look like this:

    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/productdb
        username: user
        password: password
      jpa:
        hibernate:
          ddl-auto: update
        show-sql: true
        database-platform: org.hibernate.dialect.PostgreSQLDialect
    ```

5. **Building the Project**

   To build the project and create the application JAR file, run the following Maven command:

    ```bash
    mvn clean install
    ```

   This will compile the application, run the tests, and package the project into a JAR file.

6. **Running the Application**

   Once the PostgreSQL container is up and running and the project has been built, you can run the Spring Boot
   application:

    ```bash
    mvn spring-boot:run
    ```

   The application will start on `http://localhost:8080` by default. You can access the product-related endpoints
   described below.

## Running Tests

### Unit Tests

The project uses JUnit 5 and Mockito to test the `ProductController`. To run the unit tests, use the following Maven
command:

```bash
mvn test
```

## Integration Tests

Integration tests can be run similarly, and they will interact with the database (PostgreSQL) to ensure that the product
operations work end-to-end. These tests are also run by the `mvn test` command.

## Docker Compose for PostgreSQL

The `docker-compose.yml` file is pre-configured to run a PostgreSQL container that the application will use for database
operations. This ensures that your local environment is set up quickly without needing to install PostgreSQL manually.

### Start PostgreSQL container:

```bash
docker-compose up -d
```
### Stop PostgreSQL container:
```bash
docker-compose down
```

### Access PostgreSQL container logs:
```bash
docker-compose logs postgres
```
