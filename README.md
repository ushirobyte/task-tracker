# Task Tracker API

A simple REST API for managing tasks, built with Spring Boot and Kotlin. This project demonstrates clean backend architecture with custom validation, automated testing, and containerized deployment.

## Features

- CRUD operations for tasks
- Custom validation annotations (`@ValidTitle`, `@ValidDescription`)
- Global exception handling with structured error responses
- API documentation via Swagger / OpenAPI
- Unit tests with MockK
- Docker support with multi-stage builds
- One-command deployment via Docker Compose

## Tech Stack

- **Language:** Kotlin
- **Framework:** Spring Boot 4
- **Database:** H2 (in-memory)
- **Build Tool:** Gradle
- **Testing:** JUnit 5, MockK
- **API Docs:** SpringDoc OpenAPI
- **Containerization:** Docker, Docker Compose

## Getting Started

### Prerequisites

- JDK 17+
- Docker (optional, for containerized run)

### Run Locally with Gradle

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### Run with Docker Compose (recommended)

```bash
docker-compose up -d
```

This builds the image and starts the container in detached mode.

To stop:

```bash
docker-compose down
```

## API Documentation

Once the app is running, open Swagger UI in your browser:
http://localhost:8080/swagger-ui/index.html

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| GET | `/api/tasks/status/{status}` | Get tasks by status |
| POST | `/api/tasks` | Create a new task |
| PATCH | `/api/tasks/{id}/status` | Update task status |
| DELETE | `/api/tasks/{id}` | Delete a task |

### Example Request

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Learn Kotlin", "description": "Study coroutines"}'
```

## Running Tests

```bash
./gradlew test
```

## Project Structure
src/main/kotlin/com/example/task_tracker/

├── controller/      # REST endpoints

├── service/         # Business logic

├── repository/      # Data access layer

├── model/           # JPA entities

├── dto/             # Request/response objects

├── validation/       # Custom validation annotations

└── exception/       # Global exception handling

## What I Learned

This project was built as part of a daily challenge to strengthen Spring Boot, Kotlin, and DevOps fundamentals covering REST API design, custom validation, unit testing, and Docker based deployment.

## Caching

This application uses Redis for two purposes:

**Session Storage** — User sessions are stored in Redis via Spring Session.
This allows sessions to survive application restarts and supports horizontal scaling.

**Data Caching** — Frequently accessed tasks are cached using Spring Cache abstraction:
- `@Cacheable` — returns data from Redis cache on repeated requests
- `@CachePut` — updates cache when task status changes
- `@CacheEvict` — removes cache entry when task is deleted

Cache TTL: 10 minutes. Redis runs as a separate container via docker-compose.

## Event-Driven Architecture

This application uses Apache Kafka for asynchronous event processing.

### Consumer Groups & Scaling

Tasks events are published to the `task-events` topic with 3 partitions.
Multiple application instances form a consumer group (`task-tracker-group`),
where each partition is assigned to exactly one consumer. When an instance
goes down, Kafka automatically rebalances partitions to remaining consumers.

### Error Handling

Failed message processing is handled with `@RetryableTopic`:

- **Retry**: 3 attempts with exponential backoff
- **Dead Letter Topic**: messages that fail all retries are sent to `task-events-dlt`

This ensures no messages are lost, and problematic messages don't block
processing of healthy ones.

### Topics

| Topic | Purpose |
|-------|---------|
| `task-events` | Main topic for task lifecycle events |
| `task-events-retry` | Automatic retry queue |
| `task-events-dlt` | Dead letter queue for failed messages |