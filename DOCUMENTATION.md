# Medical Record System - Technical Documentation

## Overview

The Medical Record System is a web application for managing electronic medical records. It provides a REST API and a Thymeleaf-based UI for managing doctors, patients, diagnoses, appointments, and sick leaves.

## Architecture

The system follows a hybrid monolith architecture:
- **REST API** (`@RestController`) for programmatic access
- **Thymeleaf UI** (`@Controller`) for browser-based access
- **Spring Security + Keycloak** for authentication and authorization
- **MySQL** as the primary database

## Entities

### Doctor
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| identificationNumber | String | Unique identifier |
| firstName | String | First name |
| lastName | String | Last name |
| specialty | String | Medical specialty |
| gp | boolean | Is general practitioner |

### Patient
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| firstName | String | First name |
| lastName | String | Last name |
| egn | String | Unique personal number |
| healthInsured | boolean | Insurance status |
| generalPractitioner | Doctor | Assigned GP |

### Diagnosis
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| name | String | Unique diagnosis name |
| description | String | Description |

### Appointment
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| date | LocalDate | Appointment date |
| doctor | Doctor | Attending doctor |
| patient | Patient | Patient |
| diagnosis | Diagnosis | Diagnosis |
| treatment | String | Treatment description |
| price | BigDecimal | Appointment price |

### SickLeave
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| startDate | LocalDate | Start date |
| numberOfDays | int | Duration in days |
| appointment | Appointment | Related appointment |

## REST API Endpoints

### Doctors
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | /api/doctors | ALL | Get all doctors |
| GET | /api/doctors/{id} | ALL | Get doctor by ID |
| POST | /api/doctors | ADMIN, DOCTOR | Create doctor |
| PUT | /api/doctors/{id} | ADMIN, DOCTOR | Update doctor |
| DELETE | /api/doctors/{id} | ADMIN, DOCTOR | Delete doctor |

### Patients
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | /api/patients | ALL | Get all patients |
| GET | /api/patients/{id} | ALL | Get patient by ID |
| POST | /api/patients | ADMIN, DOCTOR | Create patient |
| PUT | /api/patients/{id} | ADMIN, DOCTOR | Update patient |
| DELETE | /api/patients/{id} | ADMIN, DOCTOR | Delete patient |

### Diagnoses
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | /api/diagnoses | ALL | Get all diagnoses |
| GET | /api/diagnoses/{id} | ALL | Get diagnosis by ID |
| POST | /api/diagnoses | ADMIN, DOCTOR | Create diagnosis |
| PUT | /api/diagnoses/{id} | ADMIN, DOCTOR | Update diagnosis |
| DELETE | /api/diagnoses/{id} | ADMIN, DOCTOR | Delete diagnosis |

### Appointments
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | /api/appointments | ALL | Get all appointments |
| GET | /api/appointments/{id} | ALL | Get appointment by ID |
| GET | /api/appointments/my-appointments | PATIENT | Get own appointments |
| POST | /api/appointments | ALL | Create appointment |
| PUT | /api/appointments/{id} | ADMIN, DOCTOR | Update appointment |
| DELETE | /api/appointments/{id} | ADMIN, DOCTOR | Delete appointment |

### Sick Leaves
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | /api/sick-leaves | ALL | Get all sick leaves |
| GET | /api/sick-leaves/{id} | ALL | Get sick leave by ID |
| POST | /api/sick-leaves | ADMIN, DOCTOR | Create sick leave |
| PUT | /api/sick-leaves/{id} | ADMIN, DOCTOR | Update sick leave |
| DELETE | /api/sick-leaves/{id} | ADMIN, DOCTOR | Delete sick leave |

### Statistics
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/statistics/most-common-diagnosis | Most common diagnosis |
| GET | /api/statistics/total-price-uninsured | Total paid by uninsured |
| GET | /api/statistics/total-price-by-doctor | Total by doctor for uninsured |
| GET | /api/statistics/appointments-count-by-doctor | Appointment count by doctor |
| GET | /api/statistics/patients-count-by-gp | Patient count by GP |
| GET | /api/statistics/month-most-sick-leaves | Month with most sick leaves |
| GET | /api/statistics/doctors-most-sick-leaves | Doctors with most sick leaves |
| GET | /api/statistics/appointments-by-doctor/{id} | Appointments by doctor |
| GET | /api/statistics/patients-by-gp/{id} | Patients by GP |
| GET | /api/statistics/patients-by-diagnosis/{id} | Patients by diagnosis |
| GET | /api/statistics/appointment-history | Patient appointment history |

## Security

### Authentication
The system uses Keycloak as an OAuth2/OpenID Connect provider. Users authenticate through Keycloak's login page and receive a JWT token.

### Authorization
| Role | Permissions |
|------|-------------|
| ADMIN | Full access to all endpoints |
| DOCTOR | Read all, write appointments, patients, sick leaves |
| PATIENT | Read only, own appointment history |

## AOP Logging

All service methods are intercepted by `LoggingAspect`:
- **@Before** - Logs method name and arguments
- **@AfterReturning** - Logs return value
- **@AfterThrowing** - Logs exceptions
- **@Around** - Logs execution time

## Docker Setup

The application runs in 3 containers:

| Container | Image | Port |
|-----------|-------|------|
| medical-records-mysql | mysql:8.0 | 3307:3306 |
| medical-records-keycloak | keycloak:24.0.1 | 8080:8080 |
| medical-records-app | custom | 8084:8084 |

## Testing

The project includes three levels of testing:

### Unit Tests
Testing individual service methods with Mockito mocks.
- DoctorServiceImplTest
- PatientServiceImplTest
- DiagnosisServiceImplTest
- AppointmentServiceImplTest
- SickLeaveServiceImplTest

### Controller Tests
Testing REST endpoints with MockMvc and mocked services.
- DoctorApiControllerTest
- PatientApiControllerTest
- DiagnosisApiControllerTest
- AppointmentApiControllerTest
- SickLeaveApiControllerTest

### Integration Tests
Testing full flow with real MySQL test database.
- DoctorIntegrationTest
- PatientIntegrationTest
- DiagnosisIntegrationTest
- AppointmentIntegrationTest
- SickLeaveIntegrationTest