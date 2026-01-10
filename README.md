# 📚 StudyTrack – Backend (Spring Boot)

StudyTrack is a **student productivity & study tracking backend** built with **Spring Boot**.  
It provides secure **REST APIs** for task management, study planning, timers, analytics, and gamification features.

This backend powers the **StudyTrack React frontend**.

---

## 🚀 Features

- 🔐 **JWT Authentication & Authorization**
- 👤 **User Registration & Login**
- 📝 **Task Management** (CRUD, priority, status)
- ⏱️ **Study Timer** (Start, Pause, Resume, Stop)
- 📅 **Study Planner**
- 📊 **Analytics** (Daily & Weekly Progress)
- 🔥 **Gamification** (Streaks & Badges)
- 🛡️ **Spring Security** (Stateless REST APIs)
- 🗄️ **MySQL Database** with JPA/Hibernate

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security + JWT**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Maven**
- **Lombok**

---

## 📂 Project Structure
src/main/java/com/studyTrack
│
├── controller # REST Controllers
├── service # Business Logic Interfaces
├── service/impl # Service Implementations
├── repository # JPA Repositories
├── entity # JPA Entities
├── dto # Request & Response DTOs
├── security # JWT & Security Config
├── exception # Custom Exceptions
└── util # Utility Classes


---

## 🔐 Authentication Flow

1. User logs in with **email & password**
2. Backend generates **JWT token**
3. Token is sent in response along with user details
4. Frontend sends token in `Authorization` header

### Example Login Response

```json
{
  "token": "jwt-token",
  "user": {
    "name": "Dipak",
    "email": "dipak@email.com"
  }
}
🔑 API Endpoints (Overview)
🔐 Auth
Method	Endpoint	Description
POST	/api/auth/register	Register user
POST	/api/auth/login	Login & get JWT
📝 Tasks
Method	Endpoint
GET	/api/tasks
POST	/api/tasks
PUT	/api/tasks/{id}/complete
DELETE	/api/tasks/{id}
⏱️ Study Sessions
Method	Endpoint
POST	/api/study-sessions/start
POST	/api/study-sessions/pause
POST	/api/study-sessions/resume
POST	/api/study-sessions/stop
GET	/api/study-sessions/active
📊 Analytics
Method	Endpoint
GET	/api/analytics/today
GET	/api/analytics/weekly
🔥 Gamification
Method	Endpoint
GET	/api/gamification/streak
⚙️ Environment Configuration

Create environment variables (recommended for production):

DB_URL=jdbc:mysql://localhost:3306/studytrack_db
DB_USERNAME=root
DB_PASSWORD=yourpassword
JWT_SECRET=your-secret-key

application.properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

server.port=${PORT:8080}

▶️ Run Locally
1️⃣ Clone Repository
git clone https://github.com/yourusername/studytrack-backend.git
cd studytrack-backend

2️⃣ Build Project
mvn clean install

3️⃣ Run Application
java -jar target/*.jar


Backend will start at:

http://localhost:8080


