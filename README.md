# Backend Spring Boot Project

This is the **Spring Boot backend** of the fullstack application.

---

## How to run locally

### Prerequisites
- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)

---

### Steps to run locally

1. Open a terminal in the backend folder:
```bash
cd path/to/backend
2. Build the project:mvn clean install
3.Run the application:mvn spring-boot:run
4.The backend API will be available at:http://localhost:8089

------------
## Unit Testing — Explanation

For testing the Student Service, I used the real database.
This is because the service does not handle sensitive data and there are no critical constraints, so the impact is manageable.

However, the best practice is usually to use mock objects.
Mocks allow you to avoid modifying real data and isolate the business logic, especially when there are security or integrity constraints.

For the Admin Service, I used mocks.
This demonstrates that I understand the difference between the two approaches and can apply the appropriate solution depending on the context.

In summary, I intentionally tested one part with the real database and another with mocked dependencies to show my understanding of both concepts.

-------------------------
POST /login
POST /register
GET /students?page=1
GET /students/{id}
POST /students
PUT /students/{id}
DELETE /students/{id}

