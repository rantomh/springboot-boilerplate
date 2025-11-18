# 🚀 Spring Boot Boilerplate -- Clean Architecture (Hexagonal)

This repository is a **Spring Boot boilerplate** following **Hexagonal Architecture (Ports & Adapters)**.

It provides a robust, scalable and highly maintainable foundation for
modern backend applications: authentication, user management, i18n,
Redis, Kafka, OAuth2 security, global exception handling, Liquibase, and
more.

------------------------------------------------------------------------

## 📌 Goals of This Boilerplate

-   Compliance with **Hexagonal Architecture**
-   Domain‑centric design: Entities, DTOs, Use Cases, Ports
-   Clear separation of layers:\
    **domain → application → adapters/infrastructure**
-   Production‑ready: security, logging, config, caching, monitoring
-   Extensible (Kafka, Redis, OAuth2, events...)
-   Clean, modular, maintainable code

------------------------------------------------------------------------

## 🏗️ Architecture (Hexagonal)

    src/main/java/com/rantomah/boilerplate/
    |
    |-- adapters/          # Inputs/Outputs (REST, Events, DB, Kafka...)
    |   |-- input          # REST Controllers (Inbound Adapters)
    |
    |-- application/       # Use Cases (Business Services)
    |   |-- ports          # Ports (interfaces)
    |   |-- usecases       # Business logic implementations
    |
    |-- domain/            # Entities, DTOs, exceptions, constants
    |
    |-- infrastructure/    # JPA, Kafka, Redis, Config, Security, etc.

------------------------------------------------------------------------

## 📁 Main Structure

    .
    ├── Dockerfile
    ├── LICENSE
    ├── pom.xml
    ├── src
    │   ├── main
    │   │   ├── java/com/rantomah/boilerplate
    │   │   │   ├── Application.java
    │   │   │   ├── adapters/
    │   │   │   │   └── input/
    │   │   │   ├── application/
    │   │   │   ├── domain/
    │   │   │   ├── infrastructure/
    │   └── resources/
    │       ├── application.yml
    │       ├── db/changelog
    │       └── i18n/messages
    └── target/

------------------------------------------------------------------------

## 🔧 Technologies & Features

-   Spring Boot 3\
-   Spring Security + OAuth2\
-   Redis Cache\
-   Kafka Events\
-   JPA + Spring Data\
-   Liquibase\
-   DTO + MapStruct\
-   Global Exception Handling\
-   i18n (EN/FR)\
-   Custom Validation\
-   Full Hexagonal Architecture

------------------------------------------------------------------------

# 🚀 Quick Start

## 1️⃣ Clone the project

``` bash
git clone https://github.com/rantomh/springboot-boilerplate.git
cd springboot-boilerplate
```

------------------------------------------------------------------------

# ▶️ Run the App

### Using Maven Wrapper

Linux/macOS:

``` bash
./mvnw spring-boot:run
```

Windows:

``` bash
mvnw.cmd spring-boot:run
```

------------------------------------------------------------------------

# 🐳 Run with Docker

### Build

``` bash
docker build -t springboot-boilerplate .
```

### Run

``` bash
docker run -p 8080:8080 springboot-boilerplate
```

------------------------------------------------------------------------

# ⚙️ Configuration

Active profiles:

-   `dev`
-   `prod`

Switch profile:

``` bash
--spring.profiles.active=dev
```

------------------------------------------------------------------------

# 📚 API Endpoints (Example)

### Health

    GET /api/v1/health

### Auth

    POST /api/v1/auth/login
    POST /api/v1/auth/refresh

### Users

    POST /api/v1/users
    GET /api/v1/users
    PUT /api/v1/users/{id}
    PATCH /api/v1/users/change-password

------------------------------------------------------------------------

# 🧱 Layer Relationship

    domain/
       entities
       dto
       exceptions
       constants

    application/
       ports/input
       usecases

    adapters/
       input
       repository
       service
       mapper
       listener/event

------------------------------------------------------------------------

# 🧪 Running Tests

``` bash
./mvnw test
```

------------------------------------------------------------------------

# 🎯 Purpose

This boilerplate is designed to:

-   Start projects fast
-   Guarantee clean, maintainable architecture
-   Serve as a professional base for enterprise apps
-   Showcase **mastery of Hexagonal Architecture** in a portfolio

------------------------------------------------------------------------

# 📄 License

This project is under the **MIT License** --- free for personal and
commercial use.

------------------------------------------------------------------------

# 🙌 Author

**RantoMah**\
Senior Fullstack Developer & Software Architect
