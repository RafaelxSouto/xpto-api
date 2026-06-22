# xpto-api

Spring Boot REST API — Java 25 · Spring Boot 4.1 · PostgreSQL

## Requirements

- Java 25
- Maven 3.9+
- Docker (PostgreSQL via docker-compose)

## Running locally

```bash
docker compose up -d
mvn spring-boot:run
```

API available at `http://localhost:8080`

## Branch naming convention

| Prefix      | Purpose                                    |
|-------------|--------------------------------------------|
| `feat/`     | New features                               |
| `fix/`      | Bug fixes                                  |
| `chore/`    | Maintenance, dependencies, configuration   |
| `docs/`     | Documentation only                         |
| `hotfix/`   | Urgent fix applied directly to main via PR |

## Workflow

- All feature branches merge into `main` via Pull Request
- `main` deploys to both staging and production
