# Task Management REST API

A secure task management system built with Spring Boot. Users can register, create and assign tasks, update task statuses, leave comments, and filter tasks.

- [Task Management REST API](#task-management-rest-api)
  - [Features](#features)
  - [API Endpoints](#api-endpoints)
  - [Database and Security](#database-and-security)
  - [Setup and Run](#setup-and-run)
  - [Example Requests](#example-requests)
  <!--
  - [Future Roadmap](#future-roadmap)
  -->

## Features
- User Authentication
  - Register users with email/password.
<!--
  - JWT-based authentication.
  - Basic Auth backward compatibility.
-->

- Task Management
  - Create and view tasks.
<!--
  - Create, view, and filter tasks.
  - Assign tasks to users.
  - Update task status (`CREATED`, `IN_PROGRESS`, `COMPLETED`).
-->

<!--
- Comments
  - Add comments to tasks.
  - View comment history (sorted newest first).
  - Track comment counts per tasks.
-->

- Database and Security
  - Persistent H2 database (file-based).
  - Secured endpoints with role-based access.

---

## API Endpoints
### 1. Authentication
Endpoint|Method|Description|Auth Required
:-:|:-:|:-:|:-:
[`/api/accounts`](#post-apiaccounts)|POST|Register a new user.|No
<!--
[`/api/auth/token`](#post-apiauthtoken)|POST|Get JWT token (Basic Auth).|Basic Auth
--->

### 2. Tasks
Endpoint|Method|Description|Auth Required
:-:|:-:|:-:|:-:
[`/api/tasks`](#post-apitasks)|POST|Create a new Task.|Basic Auth
[`/api/tasks`](#get-apitasks)|GET|List all tasks (filter by `author`).|Basic Auth
<!--
`/api/tasks`|POST|Create a new task.|JWT/Basic Auth
[`/api/tasks`](#get-apitasks)|GET|List all tasks (filter by `author` / `assignee`).|JWT/Basic Auth
`/api/tasks/{taskId}/assign`|PUT|Assign/unassign a task.|Task Author
`/api/tasks/{taskId}/status`|PUT|Update task status.|Author/Assignee
--->

<!--
### 3. Comments
Endpoint|Method|Description|Auth Required
:-:|:-:|:-:|:-:
`/api/tasks/{taskId}/comments`|POST|Add a comment to a task.|JWT/Basic Auth
`/api/tasks/{taskId}/comments`|GET|List all comments for a task.|JWT/Basic Auth
--->

---

## Database and Security

- **H2 Database** (persistent storage, file-based).
- **Spring Security** configured for:
  - Stateless sessions.
  - CSRF disabled (for testing).
  - Public access to `/error`, `/actuator/shutdown`, and H2 console.

---

## Setup and Run
**Prerequisites**
- Java 17+
- Maven 3.8+
- H2 Database (embedded)

**Installation**
1. Clone the repository
```sh
git clone https://github.com/eucarizan/task-mgt-app-v1.git
```

2. Configure `application.properties`:
```properties
spring.datasource.url=jdbc:h2:file:../tms_db
spring.h2.console.enabled=true
```

3. Run the application:
```sh
mvn spring-boot:run
```

**Access H2 Console**
- URL: `http://localhost:8080/h2-console`
- Credentials:
  - Username: sa
  - Password: sa

---

## Example Requests
### 1. User Management
<!--
### 1. User Registration and Authentication
-->
#### Register a User
**Request:**
```http
POST /api/accounts HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "email": "user1@mail.com",
  "password": "password123"
}
```
**Response:** `200 OK`
<!--
#### Get JWT Token
**Request:**
```http
POST /api/auth/token
Authorization: Basic dXNlcjFAbWFpbC5jb206cGFzc3dvcmQxMjM=
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
-->

### 2. Task Management
#### Adding a Task
**Request:**
```http
POST /api/tasks HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjFAbWFpbC5jb206cGFzc3dvcmQ= # Base64 for 'user1@mail.com:password'
Content-Type: application/json

{
  "title": "new task",
  "description": "a task for anyone"
}
```

**Response:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": "1",
  "title": "new task",
  "description": "a task for anyone",
  "status": "CREATED",
  "author": "user@mail.com"
}
```


<!--
## Future Roadmap
- File attachments for tasks/comments.
- Due dates and priority levels.
- Email notifications.
- Swagger API documentation.
-->

---

## Detailed Endpoints

<a id="post-apiaccounts"></a>
- **POST** `/api/accounts`
  - Registers a new user with `email` (unique, case-insensitive) and `password` (min 6 chars).
  - **Validations**:
    - `400 BAD REQUEST` if:
      - Email/password is empty/blank.
      - Email format is invalid.
      - Password length < 6.
    - `409 CONFLICT` if email already exists.
  - **Success**: `200 OK`.

<a id="post-apitasks"></a>
- **POST** `/api/tasks`
  - Creates a new task with `title` (non-blank) and `description` (non-blank).
  - **Validations:**
    - `400 BAD REQUEST` if title/description is empty/blank.
    - `401 UNAUTHORIZED` if unauthenticated.
  - **Success Response (**`200 OK`**):**
  ```json
  {
      "id": "1",
      "title": "new task",
      "description": "a task for anyone",
      "status": "CREATED",
      "author": "user1@mail.com"
  }
  ```
  - **Status:** Defaults to `CREATED`.

<a id="get-apitasks"></a>
- **GET** `/api/tasks`
  - Secured endpoint requiring **Basic HTTP Authentication**.
  - `200 OK` if authenticated, `401 UNAUTHORIZED` otherwise.
  - Returns all tasks (newest first) or filters by `?author=email` (case-insensitive).
  - **Response (**`200 OK`**):**
  ```json
  [
    {
      "id": "1",
      "title": "new task",
      "description": "a task for anyone",
      "status": "CREATED",
      "author": "user1@mail.com"
    }
  ]
  ```
  - Requires **Basic Auth** (`401` if unauthorized).

<!--
<a id="put-apitasks-assign"></a>
- **PUT** `/api/tasks/{taskId}/assign`
  - Assigns/unassigns a task to a user (author-only).
  - **Requests:**
  ```json
  { "assignee": "user2@mail.com"} // or "none" to unassign
  ```
  - **Responses:**
    - `200 OK`: Success (returns updated task).
    - `400 BAD REQUEST`: Invalid email/format.
    - `403 FORBIDDEN`: Caller is not the author.
    - `404 NOT FOUND`: Task/asignee not found.
-->

<!--
<a id="put-apitasks-status"></a>
- **PUT** `/api/tasks/{taskId}/status`
  - Updates status (author or assignee only).
  - **Valid Statuses**: `CREATED`, `IN_PROGRESS`, `COMPLETED`.
  - **Responses:**
    - `200 OK`: Success.
    - `400 BAD REQUEST`: Invalid status.
    - `403 FORBIDDEN`: Caller is not the author nor assignee.
    - `404 NOT FOUND`: Task not found.
-->

<!--
<a id="post-apiauthtoken"></a>
- **POST** `/api/auth/token`
  - Authenticates users via **Basic Auth** and returns a JWT token.
  - **Response (**`200 OK`**):**
  ```json
  { "token": "eyJhbGci0iJ..." }
  ```
  - **Validations:**
    - `401 UNAUTHORIZED` for invalid credentials.
-->

[<<](https://github.com/eucarizan/hs-java-backend/blob/main/README.md)
<!--
:%s/\(Sample \(Input\|Output\) \d:\)\n\(.*\)/```\r\r**\1**\r```\3/gc

### 0: 
<details>
<summary></summary>

#### 0.1 description

#### 0.2 objectives

#### 0.3 examples

</details>

-->

