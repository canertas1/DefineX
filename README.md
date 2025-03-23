# Task Management

A Spring Boot backend project for task management.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Requirements](#requirements)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Screenshots](#screenshots)
- [Database](#database)
- [Configuration](#configuration)
- [Testing](#testing)
- [License](#license)

## Features  
- RESTful API endpoints for projects, tasks, users, departments  
- Project and task management  
- Team member assignment to tasks with role-based permissions  
- Progress tracking with multiple states (Backlog, In Analysis, In Development/Progress, Cancelled, Blocked, Completed)  
- Priority management system (Critical, High, Medium, Low)  
- File attachment support  
- Authentication and authorization using JWT  
- Data validation and comprehensive error handling  
- Database integration with PostgreSQL  


## Technologies Used  
- Java 21  
- Spring Boot  
- Spring Data JPA  
- Spring Security  
- PostgreSQL  
- Docker  
- Maven  
- JUnit and JaCoCo for testing and code coverage  
- Lombok  
- MapStruct  
- Git and GitHub  
- Postman for API testing  

## Requirements  
- Java JDK 21  
- Maven  
- PostgreSQL  
- Docker (optional, for containerized deployment)  
- Git for version control  


## Installation

1. Clone the repository:
```bash
git clone https://github.com/canertas1/DefineX.git
cd taskmanagement
```

2. Configure the database connection in `src/main/resources/application.yml`
```properties
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database
    username: your_username
    password: your_password
```

3. Build the project:
```bash
mvn clean install
```

## Running the Application

### Using Maven
```bash
mvn spring-boot:run
```

The application will start on http://localhost:8080

## API Endpoints

### Authentication
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/auth/login` | POST | User login |
| `/api/auth/register` | POST | User registration |

### User Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/users` | GET | Get all users |
| `/api/users/{id}` | GET | Get users by ID |
| `/api/users` | POST | Create new users |
| `/api/users/{id}` | POST | Update existing users |
| `/api/users/{id}` | DELETE | Delete users |

### Attachments Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/attachments/upload` | POST | Upload a file attachment to a task |
| `/api/attachments/download/{attachmentId}` | GET | Download a specific file attachment |
| `/api/attachments/{attachmentId}` | DELETE | Delete a specific attachment |
| `/api/attachments/task/{taskId}` | GET | Get all attachments for a specific task |

## Comment Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/comments` | POST | Create a new comment |
| `/api/comments/{id}` | PUT | Update an existing comment |
| `/api/comments/{id}` | DELETE | Delete a comment |
| `/api/comments/{id}` | GET | Get a comment by ID |

## Department Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/departments` | POST | Create a new department |
| `/api/departments/{id}` | GET | Get a department by ID |
| `/api/departments` | GET | Get all departments |
| `/api/departments/{id}` | DELETE | Delete a department |

## Project Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/projects/{departmentId}` | POST | Create a new project in a specific department |
| `/api/projects/update/{projectId}` | POST | Update an existing project |
| `/api/projects/{id}` | GET | Get a project by ID |
| `/api/projects/{id}` | DELETE | Delete a project |

## Task Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/tasks/{projectId}` | POST | Create a new task in a specific project |
| `/api/tasks/{id}` | GET | Get a task by ID |
| `/api/tasks/{id}/state` | PUT | Update the state of a task |
| `/api/tasks/{id}` | PUT | Update a task |
| `/api/tasks/{taskId}/assign/{userId}` | PATCH | Assign a task to a user |
| `/api/tasks/{id}/priority` | PUT | Change the priority of a task |
| `/api/tasks/{id}` | DELETE | Delete a task |

## Screenshots

### API Testing with Postman
![Postman API Testing](screenshots/postman-testing.png)
*Screenshot showing successful API request and response in Postman*

### API Response Example
![API Response](screenshots/api-response.png)
*Example of a successful JSON response from the API*

## Database

### Entity Relationship Diagram
![ERD](screenshots/database-erd.png)

### Database Schema
```sql
CREATE TABLE resource (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add more table definitions as needed
```

## Configuration

### Application Properties
Key application properties and what they control:

| Property | Description | Default Value |
|----------|-------------|---------------|
| `server.port` | Port the server runs on | 8080 |
| `spring.jpa.hibernate.ddl-auto` | Database schema generation | update |
| `jwt.secret` | JWT secret key for token generation | - |
| `jwt.expiration` | Token expiration time in ms | 86400000 |

### Profiles
The application supports multiple profiles:
- `dev`: Development environment settings
- `test`: Test environment settings
- `prod`: Production environment settings

To activate a profile:
```bash
java -jar -Dspring.profiles.active=prod your-application.jar
```

## Testing

Run the tests with:
```bash
mvn test
# or if using Gradle
gradle test
```

### API Testing
Examples of how to test the API with tools like Postman or curl:

```bash
# Example API test with curl
curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username": "test", "password": "password"}'
```


### Common Issues
- **Connection refused errors**: Check that your database is running and accessible
- **Authentication failures**: Verify your JWT settings and token validity
- **Missing dependencies**: Run `mvn dependency:tree` to check for dependency issues



## License
This project is licensed under the [License Name] - see the LICENSE file for details.