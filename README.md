# ![RealWorld Example App using Kotlin and Spring](example-logo.png)

[![Actions](https://github.com/gothinkster/spring-boot-realworld-example-app/workflows/Java%20CI/badge.svg)](https://github.com/gothinkster/spring-boot-realworld-example-app/actions)

> ### Spring boot + MyBatis codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld-example-apps) spec and API.

This codebase was created to demonstrate a fully fledged full-stack application built with Spring boot + Mybatis including CRUD operations, authentication, routing, pagination, and more.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.

## Features

This application provides a complete backend implementation for a Medium-like blogging platform with the following features:

- User registration and authentication with JWT tokens
- User profiles with follow/unfollow functionality
- Article CRUD operations with slug-based URLs
- Article tagging system
- Article favorites
- Comments on articles
- Feed of articles from followed users
- Both REST API and GraphQL endpoints

## GraphQL Support  

Following some DDD principles, REST or GraphQL is just a kind of adapter. The domain layer remains consistent regardless of the API type. This repository implements both GraphQL and REST simultaneously.

The GraphQL schema is located at `src/main/resources/schema/schema.graphqls` and the visualization looks like below.

![](graphql-schema.png)

This implementation uses [dgs-framework](https://github.com/Netflix/dgs-framework), Netflix's GraphQL server framework for Spring Boot.

### How it works

The application uses Spring Boot (Web, Mybatis).

- Uses Domain Driven Design (DDD) to separate business logic from infrastructure concerns
- Uses MyBatis to implement the [Data Mapper](https://martinfowler.com/eaaCatalog/dataMapper.html) pattern for persistence
- Uses [CQRS](https://martinfowler.com/bliki/CQRS.html) pattern to separate read and write models
- Uses Lombok to reduce boilerplate code

## Project Structure

```
src/main/java/io/spring/
├── api/                    # REST API layer (Spring MVC controllers)
│   ├── exception/          # Custom exceptions and error handling
│   └── security/           # JWT filter and security configuration
├── application/            # Application services layer
│   ├── article/            # Article command services and validation
│   ├── data/               # Data Transfer Objects (DTOs)
│   └── user/               # User command services and validation
├── core/                   # Domain layer (entities and repository interfaces)
│   ├── article/            # Article entity and repository
│   ├── comment/            # Comment entity and repository
│   ├── favorite/           # ArticleFavorite entity and repository
│   ├── service/            # Domain service interfaces (JwtService, AuthorizationService)
│   └── user/               # User entity, FollowRelation, and repository
├── graphql/                # GraphQL layer (Netflix DGS datafetchers and mutations)
│   └── exception/          # GraphQL-specific exceptions
└── infrastructure/         # Infrastructure layer (implementations)
    ├── mybatis/            # MyBatis mappers, type handlers, and read services
    ├── repository/         # Repository implementations
    └── service/            # Service implementations (DefaultJwtService)
```

## Requirements

- Java 11 or higher
- Gradle (wrapper included)

## Dependencies

The main dependencies used in this project:

| Dependency | Purpose |
|------------|---------|
| Spring Boot 2.6.3 | Application framework |
| Spring Security | Authentication and authorization |
| MyBatis | ORM/Data Mapper |
| Netflix DGS | GraphQL server framework |
| JJWT | JWT token generation and validation |
| Joda-Time | Date/time handling |
| SQLite | Database (default) |
| Lombok | Boilerplate code reduction |
| Flyway | Database migrations |

## Security

Integration with Spring Security with JWT token-based authentication.

The JWT configuration is stored in `application.properties`:
- `jwt.secret`: Secret key for signing tokens (HS512 algorithm)
- `jwt.sessionTime`: Token expiration time in seconds (default: 86400 = 24 hours)

## Database

Uses SQLite database by default for easy local testing without losing data after restart. Can be changed in `application.properties` for any other database.

## Getting started

You'll need Java 11 installed.

```bash
./gradlew bootRun
```

To test that it works, open a browser tab at http://localhost:8080/tags or run:

```bash
curl http://localhost:8080/tags
```

## Configuration

The main configuration file is `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:sqlite:dev.db
spring.datasource.driver-class-name=org.sqlite.JDBC

# JWT
jwt.secret=your-secret-key
jwt.sessionTime=86400

# MyBatis
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.mapper-locations=mapper/*.xml
```

## API Examples

### User Registration

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "user": {
      "username": "johndoe",
      "email": "john@example.com",
      "password": "password123"
    }
  }'
```

### User Login

```bash
curl -X POST http://localhost:8080/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "user": {
      "email": "john@example.com",
      "password": "password123"
    }
  }'
```

### Get Current User

```bash
curl http://localhost:8080/user \
  -H "Authorization: Token <your-jwt-token>"
```

### Create Article

```bash
curl -X POST http://localhost:8080/articles \
  -H "Content-Type: application/json" \
  -H "Authorization: Token <your-jwt-token>" \
  -d '{
    "article": {
      "title": "How to train your dragon",
      "description": "Ever wonder how?",
      "body": "You have to believe",
      "tagList": ["dragons", "training"]
    }
  }'
```

### Get Articles

```bash
# Get all articles
curl http://localhost:8080/articles

# Filter by tag
curl http://localhost:8080/articles?tag=dragons

# Filter by author
curl http://localhost:8080/articles?author=johndoe

# Filter by favorited
curl http://localhost:8080/articles?favorited=johndoe

# Pagination
curl http://localhost:8080/articles?limit=10&offset=0
```

### Get User Feed

```bash
curl http://localhost:8080/articles/feed \
  -H "Authorization: Token <your-jwt-token>"
```

### Favorite Article

```bash
curl -X POST http://localhost:8080/articles/how-to-train-your-dragon/favorite \
  -H "Authorization: Token <your-jwt-token>"
```

### Follow User

```bash
curl -X POST http://localhost:8080/profiles/johndoe/follow \
  -H "Authorization: Token <your-jwt-token>"
```

### Add Comment

```bash
curl -X POST http://localhost:8080/articles/how-to-train-your-dragon/comments \
  -H "Content-Type: application/json" \
  -H "Authorization: Token <your-jwt-token>" \
  -d '{
    "comment": {
      "body": "Great article!"
    }
  }'
```

## GraphQL API

The GraphQL endpoint is available at `http://localhost:8080/graphql`.

Example query:

```graphql
query {
  articles(first: 10) {
    edges {
      node {
        slug
        title
        description
        author {
          username
        }
      }
    }
  }
}
```

## Try it out with Docker

You'll need Docker installed.

```bash
./gradlew bootBuildImage --imageName spring-boot-realworld-example-app
docker run -p 8081:8080 spring-boot-realworld-example-app
```

## Try it out with a RealWorld frontend

The entry point address of the backend API is at http://localhost:8080, **not** http://localhost:8080/api as some of the frontend documentation suggests.

## Run tests

The repository contains comprehensive test cases covering both API tests and repository tests.

```bash
./gradlew test
```

## Code format

Use Spotless for code formatting with Google Java Format.

```bash
./gradlew spotlessJavaApply
```

## Contributing

Please fork and PR to improve the project.
