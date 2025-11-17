
### Hexagonal / Clean Architecture Principles

- **Domain Layer**: pure business entities, no Spring dependencies.
- **Application Layer**: use-case interfaces with meaningful method names.
- **Adapters**: handle infrastructure concerns (REST, JPA, Redis cache).

---

##️ Technologies Used

- Java 21+
- Spring Boot 3+
- Spring Data JPA
- PostgreSQL
- MapStruct
- Spring Cache with Redis
- REST API (Spring Web)
- Maven
- Docker + Docker Compose (Postgres + Redis for development)
- Testcontainers (integration testing)

---
