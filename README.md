# Spring Boot Microservices Learning Project

A microservices-based application built with Spring Boot 3 and Spring Cloud to learn modern distributed system architecture.

## Architecture

- Config Server
- Eureka Discovery Server
- Spring Cloud Gateway
- Auth Service
- User Service
- PostgreSQL / MySQL
- OpenFeign for inter-service communication

## Implemented Features

### Infrastructure
- Centralized configuration using Spring Cloud Config Server
- Service discovery using Eureka Server
- API Gateway for routing client requests
- Configuration management through a GitHub repository

### User Service
- Create User API
- Get User by ID API
- Get User by Email API
- Database integration with Spring Data JPA

### Auth Service
- User registration
- OTP generation and email delivery
- OTP verification
- Communication with User Service using OpenFeign

### Gateway
- Request routing to microservices
- Eureka-based load-balanced routing (`lb://`)
- External entry point for all client requests

### Service Communication
- OpenFeign integration
- Service discovery through Eureka
- No hardcoded service URLs

## Technologies

- Java 21
- Spring Boot 3
- Spring Cloud
- Spring Cloud Gateway
- Spring Cloud Config
- Spring Cloud OpenFeign
- Netflix Eureka
- Spring Data JPA
- MySQL / PostgreSQL
- Maven

## Current Flow

```
Client
   │
   ▼
API Gateway
   │
   ├────────► Auth Service
   │              │
   │              ▼
   │        OpenFeign Client
   │              │
   ▼              ▼
User Service ───► Database
```

## Next Steps

- Password encryption (BCrypt)
- JWT Authentication
- Spring Security
- Protected APIs
- Role-Based Authorization (RBAC)
- Refresh Tokens
- Global Exception Handling
- Distributed Tracing
- Docker & Docker Compose
- Kubernetes Deployment


- <img width="1912" height="1102" alt="image" src="https://github.com/user-attachments/assets/8af4c593-9a3f-44af-8a48-77cb5e82e398" />

