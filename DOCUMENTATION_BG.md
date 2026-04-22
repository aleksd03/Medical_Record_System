# Система за Медицинско Досие - Техническа Документация

## Общ преглед

Системата за медицинско досие е уеб приложение за управление на електронни медицински досиета. Предоставя REST API и Thymeleaf базиран интерфейс за управление на лекари, пациенти, диагнози, прегледи и болнични листове.

## Архитектура

Системата следва хибридна монолитна архитектура:
- **REST API** (`@RestController`) за програмен достъп
- **Thymeleaf UI** (`@Controller`) за браузър базиран достъп
- **Spring Security + Keycloak** за автентикация и оторизация
- **MySQL** като основна база данни

## Entities (Обекти)

### Doctor (Лекар)
| Поле | Тип | Описание |
|------|-----|----------|
| id | Long | Първичен ключ |
| identificationNumber | String | Уникален идентификационен номер |
| firstName | String | Име |
| lastName | String | Фамилия |
| specialty | String | Специалност |
| gp | boolean | Личен лекар |

### Patient (Пациент)
| Поле | Тип | Описание |
|------|-----|----------|
| id | Long | Първичен ключ |
| firstName | String | Име |
| lastName | String | Фамилия |
| egn | String | ЕГН |
| healthInsured | boolean | Здравно осигурен |
| generalPractitioner | Doctor | Личен лекар |

### Diagnosis (Диагноза)
| Поле | Тип | Описание |
|------|-----|----------|
| id | Long | Първичен ключ |
| name | String | Уникално наименование |
| description | String | Описание |

### Appointment (Преглед)
| Поле | Тип | Описание |
|------|-----|----------|
| id | Long | Първичен ключ |
| date | LocalDate | Дата на преглед |
| doctor | Doctor | Лекар |
| patient | Patient | Пациент |
| diagnosis | Diagnosis | Диагноза |
| treatment | String | Лечение |
| price | BigDecimal | Цена |

### SickLeave (Болничен лист)
| Поле | Тип | Описание |
|------|-----|----------|
| id | Long | Първичен ключ |
| startDate | LocalDate | Начална дата |
| numberOfDays | int | Брой дни |
| appointment | Appointment | Свързан преглед |

## REST API Endpoints

### Лекари
| Метод | Endpoint | Достъп | Описание |
|-------|----------|--------|----------|
| GET | /api/doctors | Всички | Всички лекари |
| GET | /api/doctors/{id} | Всички | Лекар по ID |
| POST | /api/doctors | ADMIN, DOCTOR | Създай лекар |
| PUT | /api/doctors/{id} | ADMIN, DOCTOR | Редактирай лекар |
| DELETE | /api/doctors/{id} | ADMIN, DOCTOR | Изтрий лекар |

### Пациенти
| Метод | Endpoint | Достъп | Описание |
|-------|----------|--------|----------|
| GET | /api/patients | Всички | Всички пациенти |
| GET | /api/patients/{id} | Всички | Пациент по ID |
| POST | /api/patients | ADMIN, DOCTOR | Създай пациент |
| PUT | /api/patients/{id} | ADMIN, DOCTOR | Редактирай пациент |
| DELETE | /api/patients/{id} | ADMIN, DOCTOR | Изтрий пациент |

### Диагнози
| Метод | Endpoint | Достъп | Описание |
|-------|----------|--------|----------|
| GET | /api/diagnoses | Всички | Всички диагнози |
| GET | /api/diagnoses/{id} | Всички | Диагноза по ID |
| POST | /api/diagnoses | ADMIN, DOCTOR | Създай диагноза |
| PUT | /api/diagnoses/{id} | ADMIN, DOCTOR | Редактирай диагноза |
| DELETE | /api/diagnoses/{id} | ADMIN, DOCTOR | Изтрий диагноза |

### Прегледи
| Метод | Endpoint | Достъп | Описание |
|-------|----------|--------|----------|
| GET | /api/appointments | Всички | Всички прегледи |
| GET | /api/appointments/{id} | Всички | Преглед по ID |
| GET | /api/appointments/my-appointments | PATIENT | Моите прегледи |
| POST | /api/appointments | Всички | Създай преглед |
| PUT | /api/appointments/{id} | ADMIN, DOCTOR | Редактирай преглед |
| DELETE | /api/appointments/{id} | ADMIN, DOCTOR | Изтрий преглед |

### Болнични листове
| Метод | Endpoint | Достъп | Описание |
|-------|----------|--------|----------|
| GET | /api/sick-leaves | Всички | Всички болнични |
| GET | /api/sick-leaves/{id} | Всички | Болничен по ID |
| POST | /api/sick-leaves | ADMIN, DOCTOR | Създай болничен |
| PUT | /api/sick-leaves/{id} | ADMIN, DOCTOR | Редактирай болничен |
| DELETE | /api/sick-leaves/{id} | ADMIN, DOCTOR | Изтрий болничен |

### Справки
| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | /api/statistics/most-common-diagnosis | Най-честа диагноза |
| GET | /api/statistics/total-price-uninsured | Обща сума от неосигурени |
| GET | /api/statistics/total-price-by-doctor | Сума по лекар за неосигурени |
| GET | /api/statistics/appointments-count-by-doctor | Брой прегледи по лекар |
| GET | /api/statistics/patients-count-by-gp | Брой пациенти по личен лекар |
| GET | /api/statistics/month-most-sick-leaves | Месец с най-много болнични |
| GET | /api/statistics/doctors-most-sick-leaves | Лекари с най-много болнични |
| GET | /api/statistics/appointments-by-doctor/{id} | Прегледи по лекар |
| GET | /api/statistics/patients-by-gp/{id} | Пациенти по личен лекар |
| GET | /api/statistics/patients-by-diagnosis/{id} | Пациенти по диагноза |
| GET | /api/statistics/appointment-history | История на прегледите |

## Сигурност

### Автентикация
Системата използва Keycloak като OAuth2/OpenID Connect доставчик. Потребителите се автентикират през Keycloak и получават JWT токен.

### Оторизация
| Роля | Права |
|------|-------|
| ADMIN | Пълен достъп до всички ресурси |
| DOCTOR | Четене на всичко, писане на прегледи, пациенти, болнични |
| PATIENT | Само четене, история на собствените прегледи |

## AOP Логване

Всички service методи се прехващат от `LoggingAspect`:
- **@Before** - Логва името на метода и аргументите
- **@AfterReturning** - Логва върнатата стойност
- **@AfterThrowing** - Логва изключенията
- **@Around** - Логва времето за изпълнение

## Docker конфигурация

Приложението работи в 3 контейнера:

| Контейнер | Image | Порт |
|-----------|-------|------|
| medical-records-mysql | mysql:8.0 | 3307:3306 |
| medical-records-keycloak | keycloak:24.0.1 | 8080:8080 |
| medical-records-app | custom | 8084:8084 |

## Тестване

Проектът включва три нива на тестване:

### Unit тестове
Тестване на отделни service методи с Mockito.
- DoctorServiceImplTest
- PatientServiceImplTest
- DiagnosisServiceImplTest
- AppointmentServiceImplTest
- SickLeaveServiceImplTest

### Controller тестове
Тестване на REST endpoints с MockMvc.
- DoctorApiControllerTest
- PatientApiControllerTest
- DiagnosisApiControllerTest
- AppointmentApiControllerTest
- SickLeaveApiControllerTest

### Integration тестове
Тестване на целия flow с реална MySQL тестова база.
- DoctorIntegrationTest
- PatientIntegrationTest
- DiagnosisIntegrationTest
- AppointmentIntegrationTest
- SickLeaveIntegrationTest

## Тестови данни

| Потребител | Парола | Роля |
|------------|--------|------|
| admin | admin123 | ADMIN |
| doctor | doctor123 | DOCTOR |
| patient | patient123 | PATIENT |