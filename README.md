# Medical Record System

A comprehensive web-based Medical Record System built with Spring Boot, providing full management of medical records, doctors, patients, appointments, diagnoses, and sick leaves.

## Tech Stack

- **Backend:** Java 21, Spring Boot 3.5
- **Security:** Keycloak OAuth2 / OpenID Connect
- **Database:** MySQL 8.0
- **ORM:** Hibernate / Spring Data JPA
- **Frontend:** Thymeleaf
- **Build Tool:** Gradle
- **Containerization:** Docker, Docker Compose
- **Testing:** JUnit 5, Mockito, Spring Boot Test
- **Other:** AOP Logging, ModelMapper, Lombok

## Features

- Full CRUD for Doctors, Patients, Diagnoses, Appointments and Sick Leaves
- Role-based access control (ADMIN, DOCTOR, PATIENT)
- OAuth2 authentication via Keycloak
- 11 statistical reports
- AOP logging for all service methods
- REST API + Thymeleaf UI
- Dockerized deployment

## Roles & Permissions

| Role    | Permissions                                          |
|---------|------------------------------------------------------|
| ADMIN   | Full access to all resources                         |
| DOCTOR  | Manage patients, appointments, sick leaves           |
| PATIENT | View own appointment history                         |

## Statistics & Reports

1. Most common diagnosis
2. Total amount paid by uninsured patients
3. Amount paid per doctor for uninsured patients
4. Appointment count per doctor
5. Patient count per general practitioner
6. Month with most sick leaves issued
7. Doctors with most sick leaves issued
8. Appointments by doctor
9. Patients by general practitioner
10. Patients by diagnosis
11. Patient appointment history

## Getting Started

### Prerequisites

- Java 21
- MySQL 8.0
- Docker & Docker Compose

### Run with Docker

1. Clone the repository:
```bash
git clone https://github.com/aleksd03/Medical_Record_System
cd Medical_Record_System
```

2. Start all services:
```bash
docker-compose up --build
```

3. Configure Keycloak at **http://localhost:8080/admin** (admin/admin123):
    - Create realm: `medical-records`
    - Create roles: `ADMIN`, `DOCTOR`, `PATIENT`
    - Create client: `medical-records-app`
    - Create users with roles

4. Access the application at **http://localhost:8084**

### Run Locally

1. Create `src/main/resources/application-local.properties`:
```properties
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:8080/realms/medical-records
spring.security.oauth2.client.registration.keycloak.client-id=medical-records-app
spring.security.oauth2.client.registration.keycloak.client-secret=YOUR_CLIENT_SECRET
spring.security.oauth2.client.registration.keycloak.scope=openid,profile,email
spring.security.oauth2.client.registration.keycloak.authorization-grant-type=authorization_code
```

2. Start MySQL and Keycloak with Docker:
```bash
docker-compose up mysql keycloak -d
```

3. Run the application from IntelliJ or:
```bash
./gradlew bootRun
```

## Project Structure
src/main/java/com/nbu/medicalrecords/
├── aspect/          - AOP Logging
├── config/          - Security Configuration
├── controller/
│   ├── api/         - REST Controllers
│   └── view/        - Thymeleaf Controllers
├── data/
│   ├── entity/      - JPA Entities
│   └── repository/  - Spring Data Repositories
├── dto/             - Data Transfer Objects
├── exception/       - Custom Exceptions
└── service/         - Business Logic

## Test Credentials

| Username | Password   | Role    |
|----------|------------|---------|
| admin    | admin123   | ADMIN   |
| doctor   | doctor123  | DOCTOR  |
| patient  | patient123 | PATIENT |

## Running Tests

```bash
./gradlew test
```

## License

This project is developed for educational purposes at NBU (New Bulgarian University).