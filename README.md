# 🏥 Hospital Management System — Spring Boot, JPA, Security

A complete **backend** for hospital operations built with **Spring Boot 3.5.3**, **Java 21**, **Spring Data JPA (Hibernate)**, **Spring Security (JWT + optional OAuth2 login)**, and **PostgreSQL**.  
It exposes REST APIs for **patients, doctors, appointments, departments, and insurance** with role-based access (ADMIN / DOCTOR / PATIENT).

> **Context Path:** `/api/v1` (configured via `server.servlet.context-path`)

---

## ✨ Features

- **Auth & RBAC:** JWT authentication, role-based authorization (ADMIN, DOCTOR, PATIENT)
- **Resource Management:** Doctors, Patients, Appointments, Departments, Insurance
- **Public Directory:** List all doctors without authentication
- **Centralized Errors:** Consistent API errors via a global handler
- **(Optional) OAuth2:** Google, GitHub, Twitter sign-in (properties shown below)

---

## 🧰 Tech Stack

- **Language:** Java **21**
- **Framework:** Spring Boot **3.5.3**
- **Security:** Spring Security, JWT (jjwt 0.12.6), optional OAuth2 client
- **Persistence:** Spring Data JPA (Hibernate) + **PostgreSQL**
- **Build:** Maven
- **Utilities:** Lombok, ModelMapper

---

## 📦 Project Structure (high level)

```
src/main/java/com/harshit/HospitalManagementApplication
 ├─ config/                 # App-level config
 ├─ controller/             # REST controllers (Auth, Admin, Doctor, Patient, Public)
 ├─ dto/                    # Request/response DTOs
 ├─ entity/                 # JPA entities (User, Patient, Doctor, Appointment, etc.)
 ├─ repository/             # Spring Data repositories
 ├─ security/               # JWT filter, utilities, WebSecurity config, OAuth handlers
 ├─ error/                  # ApiError + GlobalExceptionHandler
 └─ HospitalManagementApplication.java
```
**Key Enums:** `RoleType` (ADMIN, DOCTOR, PATIENT), `PermissionType` (fine‑grained permissions).

---

## ⚙️ Configuration — `application.properties` (copy–paste)

Below is a **single properties file** that covers DB, JPA, JWT and optional OAuth2. Replace placeholders with your values.

```properties
# App
spring.application.name=HospitalManagementApplication
server.port=8080
server.servlet.context-path=/api/v1

# --- Database (PostgreSQL) ---
spring.datasource.url=jdbc:postgresql://localhost:5432/hospitalDB
spring.datasource.username=postgres
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver (optional)

# --- JPA & Hibernate ---
# NOTE: "create" will create tables on startup (and can drop them). Use "update" for dev safety.
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
# spring.jpa.properties.hibernate.format_sql=true

# --- Sample Data (optional) ---
# data.sql is included. Uncomment these to auto-load sample doctors/patients/appointments.
# spring.jpa.defer-datasource-initialization=true
# spring.sql.init.mode=always
# spring.sql.init.data-locations=classpath:data.sql (here data.sql is the name of you sql file where you have placed your demo data)

# --- Security: JWT ---
# Use a strong, long, random value; keep it secret.
jwt.secretKey=REPLACE_WITH_YOUR_LONG_RANDOM_SECRET

# --- (Optional) OAuth2 Login ---
# Enable if you want Google/GitHub/Twitter sign-in. Remove if unused.
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET

spring.security.oauth2.client.registration.github.client-id=YOUR_GITHUB_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=YOUR_GITHUB_CLIENT_SECRET

spring.security.oauth2.client.registration.twitter.client-id=YOUR_TWITTER_CLIENT_ID
spring.security.oauth2.client.registration.twitter.client-secret=YOUR_TWITTER_CLIENT_SECRET
spring.security.oauth2.client.registration.twitter.redirect-uri={baseUrl}/login/oauth2/code/twitter
spring.security.oauth2.client.registration.twitter.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.twitter.scope=tweet.read, users.read, offline.access
spring.security.oauth2.client.registration.twitter.client-name=Twitter
spring.security.oauth2.client.registration.twitter.provider=twitter

spring.security.oauth2.client.provider.twitter.authorization-uri=https://twitter.com/i/oauth2/authorize
spring.security.oauth2.client.provider.twitter.token-uri=https://api.twitter.com/2/oauth2/token
spring.security.oauth2.client.provider.twitter.user-info-uri=https://api.twitter.com/2/users/me
spring.security.oauth2.client.provider.twitter.user-name-attribute=id
```

> **Default DB Name:** `hospitalDB`. Create it in Postgres (`CREATE DATABASE hospitalDB;`).

---

## ▶️ Run Locally

**Prerequisites:** JDK 21, Maven 3.9+, PostgreSQL 14+

```bash
# 1) Clone
git clone <your-repo-url>
cd hospital-management-system

# 2) Configure application.properties (see above)

# 3) Build & Run
mvn clean install
mvn spring-boot:run
# App -> http://localhost:8080/api/v1
```

---

## 🔒 Security & Access Rules

- `GET /public/**` and `/auth/**` → **Permit All**
- `/admin/**` → **ROLE_ADMIN** (DELETE under admin additionally requires `APPOINTMENT_DELETE` or `USER_MANAGE` authority)
- `/doctors/**` → **ROLE_DOCTOR** or **ROLE_ADMIN**
- Any other request → **authenticated**

**JWT Usage**
- Header: `Authorization: Bearer <token>`
- Obtain token via `/auth/login`

---

## 🧪 Quick Test (cURL)

**1) Sign up**
```bash
curl -X POST http://localhost:8080/api/v1/auth/signup   -H "Content-Type: application/json"   -d '{
        "username":"harshit",
        "password":"pass123",
        "name":"Harshit Bansal",
        "roles":["PATIENT"]
      }'
```

**2) Login (receive `jwt` + `userId`)**
```bash
curl -X POST http://localhost:8080/api/v1/auth/login   -H "Content-Type: application/json"   -d '{"username":"harshit","password":"pass123"}'
```

**3) Public doctors (no auth)**
```bash
curl http://localhost:8080/api/v1/public/doctors
```

**4) Patient: create appointment**
```bash
# Replace TOKEN with your JWT
curl -X POST http://localhost:8080/api/v1/patients/appointments   -H "Authorization: Bearer TOKEN"   -H "Content-Type: application/json"   -d '{
        "doctorId": 1,
        "patientId": 1,
        "appointmentTime": "2025-06-25T10:00:00",
        "reason": "Health Checkup"
      }'
```

**5) Doctor: view own appointments**
```bash
curl -H "Authorization: Bearer TOKEN"   http://localhost:8080/api/v1/doctors/appointments
```

**6) Admin: onboard a doctor**
```bash
curl -X POST http://localhost:8080/api/v1/admin/onBoardNewDoctor   -H "Authorization: Bearer TOKEN"   -H "Content-Type: application/json"   -d '{
        "userId": 3,
        "name": "Dr. vikash",
        "specialization": "orthologist"
      }'
```

---

## 📚 API Overview (DTO-driven)

**Auth**
- `POST /auth/signup` → Body: `SignUpRequestDto {username,password,name,roles[]}` → `SignupResponseDto {id,username}`
- `POST /auth/login` → Body: `LoginRequestDto {username,password}` → `LoginResponseDto {jwt,userId}`

**Public**
- `GET /public/doctors` → `List<DoctorResponseDto {id,name,specialization,email}>`

**Patients**
- `POST /patients/appointments` → Body: `CreateAppointmentRequestDto {doctorId,patientId,appointmentTime,reason}`  
  → `AppointmentResponseDto {id,appointmentTime,reason,doctor,patient}`
- `GET /patients/profile` → `PatientResponseDto {id,name,gender,birthDate,bloodGroup}`

**Doctors**
- `GET /doctors/appointments` → list assigned appointments

**Admin**
- `GET /admin/patients` → `List<PatientResponseDto>`
- `POST /admin/onBoardNewDoctor` → Body: `OnboardDoctorRequestDto {userId,name,specialization}`

> Full paths include the context path, e.g., `/api/v1/auth/login`.

---

## ❌ Error Response Shape

The global handler returns a consistent error body:

```json
{
  "timeStamp": "2025-08-22T12:34:56.123",
  "error": "Unauthorized",
  "statusCode": "UNAUTHORIZED"
}
```

---

## 🗃️ Data Model (simplified)

- **User** — id, username, password, roles (ADMIN/DOCTOR/PATIENT)
- **Patient** — id, name, gender, birthDate, email, bloodGroup, insurance
- **Doctor** — id, name, specialization, email, departments, appointments
- **Appointment** — id, appointmentTime (LocalDateTime), reason, doctor (ManyToOne), patient (ManyToOne)
- **Department**, **Insurance** — basic reference entities

---

## 🧑‍💻 Development Notes

- **DTO-first** API to keep entities isolated from transport
- **JWT Filter** reads `Authorization: Bearer <token>` and authenticates requests
- **Role + Permission** checks configured in `WebSecurityConfig`
- **Seed data** available in `data.sql` (see properties above)

---

## 📄 License

Educational/demo purposes. Adapt as needed for production.
