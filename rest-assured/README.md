# MyToDo REST Assured API Testing

This project provides automated API tests for the MyToDo application using REST Assured, TestNG, and Java. It covers authentication and todo CRUD operations, including both positive and negative scenarios.

## Requirements
- Java 17+
- Maven 3.6+
- Docker (for backend services)

## Tools Used & Why
- **REST Assured:** Fluent, readable, and robust API test automation in Java.
- **TestNG:** Test organization, grouping, dependencies, and reporting.
- **Maven:** Dependency management and build lifecycle.
- **Allure:** Rich, visual test reporting (optional, but supported).
- **Docker:** Running backend services in a consistent, isolated environment.

## What is Tested?
- **Authentication:** Login, register, and error scenarios for user authentication.
- **Todo CRUD:** Create, Read (summary and by ID), Update, Delete operations for todo items.
- **Negative Scenarios:** Error handling for invalid input, missing fields, unauthorized access, and operations on non-existent resources.

## Test Coverage Areas
- **Authentication:**
  - Successful login
  - Invalid email/password
  - Empty/missing fields
  - Malformed JSON
- **Todo:**
  - Create todo
  - Get todo summary
  - Get todo by ID
  - Update todo (task, category, datetime, status)
  - Delete todo
  - Verify deletion
  - Error cases: missing fields, empty task, non-existent todo, unauthorized access

## Setup & How to Run

1. **Start Backend Services**
   ```bash
   docker-compose up -d
   ```
2. **(Optional) Create Test User**
   ```bash
   curl -X POST http://localhost:9000/auth/register \
     -H "Content-Type: application/json" \
     -d '{
       "username": "apitestuser",
       "email": "apitest@example.com",
       "password": "testpassword123",
       "phone": "1234567890"
     }'
   ```
3. **Run All Tests**
   ```bash
   mvn clean test
   ```
4. **Run Specific Test Class**
   ```bash
   mvn test -Dtest=AuthenticationApiTest
   mvn test -Dtest=ItemsApiTest
   ```

## Assumptions & Limitations
- Backend services are running and accessible at `http://localhost:9000`.
- The test user exists or is created before running authentication tests.
- The database is not pre-populated with conflicting test data; tests are designed to be idempotent and clean up after themselves.
- Tests are ordered: negative cases first, then create → get → update → delete for todos.
- No UI or end-to-end browser tests are included; only API endpoints are tested.
- Allure reporting is optional and requires additional setup (`mvn allure:serve`).

## Project Structure
```
rest-assured/
├── src/test/java/com/mytodo/
│   ├── api/         # Test classes
│   ├── constants/   # Constants (endpoints, test data, etc.)
│   ├── enums/       # Enums
│   ├── models/      # Request/response models
│   ├── services/    # API clients
│   └── spec/        # Request/response specifications
├── testng.xml       # TestNG configuration
├── pom.xml          # Maven project file
├── .gitignore       # Git ignore rules
└── README.md        # This file
```

---

**Tested:** All tests pass successfully after cleanup. For further details, see the code and comments in the test classes under `src/test/java/com/mytodo/api/`.