# Event Management System API

## Description
The Event Management System is a Spring Boot-based REST API designed to manage buildings, events, tickets, and users. It includes functionalities such as:
- CRUD operations for entities like buildings, events, and tickets.
- Authentication and authorization using Spring Security with JWT tokens.
- RESTful API endpoints for data management.
- OpenAPI documentation for easy integration.

## Features
- **Building Management**: Create, view, update, and delete buildings.
- **Event Management**: Link events to specific buildings, manage tickets.
- **User Authentication**: Secure login and role-based access control with JWT.
- **RESTful API**: Clean REST endpoints for all operations.

---

## Technologies Used
- **Backend**: Spring Boot (Spring Web, Spring Data JPA, Spring Security)
- **Database**: H2 (in-memory database, switchable to PostgreSQL or MySQL)
- **Dependencies**:
  - Hibernate Validator
  - ModelMapper for DTO mapping
  - REST Client for API integrations
  - SpringDoc OpenAPI for API documentation

---

## Setup Instructions

### Prerequisites
- Java 21 or newer
- Gradle
- Git
