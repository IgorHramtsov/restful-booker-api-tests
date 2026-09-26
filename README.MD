# Restful Booker API Tests

Pet project for API test automation using the Restful Booker API.

The project covers basic CRUD operations, authentication, negative scenarios and a full booking flow.

## Stack

- Java 21
- RestAssured
- TestNG
- Jackson
- Maven
- Allure
- Jenkins
- Docker

## Project structure

- `client` — API requests
- `dto` — request and response models
- `factory` — test data
- `assertions` — reusable assertions
- `specs` — RestAssured specifications
- `tests` — API tests

## Running tests

Create `src/test/resources/booker.properties` using `booker.properties.example` as a template.

Run all tests:

```bash
mvn test
```

Run smoke tests:

```bash
mvn test -Dgroups=smoke
```

Run regression tests:

```bash
mvn test -Dgroups=regression
```

## Allure

Generate and open the Allure report:

```bash
mvn allure:serve
```

## Docker

The project can also be built and executed using Docker:

```bash
docker compose up --build
```

## CI

`Jenkinsfile` contains a pipeline for running all, smoke or regression tests and publishing test reports.