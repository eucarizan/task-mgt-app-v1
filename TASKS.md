# Task Management REST API tasks

- [Task Management REST API tasks](#task-management-rest-api-tasks)
  - [Epics](#epics)
  - [User Stories](#user-stories)
  - [Tasks](#tasks)

## Epics 
- [User Management](#e1-user-management)
<!-- 
- [Task Management](#e2-task-management)
-->

## User Stories
- [US01: Register New User](#user-story-01-register-new-user)
- [US02: Secure Tasks Endpoint](#user-story-02-secure-tasks-endpoint)
<!--
- [US03: Create a Task](#user-story-03-create-a-task)
-->

## Tasks
- [Task01: Implement H2 Database Setup](#task-01-implement-h2-database-setup)
- [Task02: Configure Spring Security](#task-02-configure-spring-security)
- [Task03: Create User Entity and Repository](#task-03-create-user-entity-and-repository)
- [Task04: Build Registration Endpoint](#task-04-build-registration-endpoint)
- [Task05: Test Edge Cases](#task-05-test-edge-cases)
<!--
- [Task00: ](#task-00-)
-->

---

### E1 User Management
_Enable user registration and secure API access._

#### User Story 01: Register New User
<details>

<summary>
<strong>As a</strong> new user,
<strong>I want to</strong> register with my email and password,
<strong>So that</strong> I can access the task management system.
</summary>

**Acceptance Criteria**:
- POST `/api/accounts` accepts JSON:
```json
{ "email": "user@example.com", "password": "validPass123" }
```
- Returns `200 0K` on success.
- Validates:
  - `400` if email/password is empty, invalid, or password < 6 chars.
  - `409` if email exists (case-insensitive check).
- Stores user in H2 database (persistent).

**Technical Notes**:
- User `@Email` validation for email format.
- Password stored encoded (e.g., BCrypt).
</details>

#### User Story 02: Secure Tasks Endpoint
<details>

<summary>
<strong>As a</strong> registred user,
<strong>I want to</strong> authenticate to view tasks,
<strong>So that</strong> only authorized users can access data.
</summary>

**Acceptance Criteria**:
- GET `/api/tasks` requires Basic Auth
- Returns `200 OK` for valid credentials.
- Returns `401 UNAUTHORIZED` for invalid/missing credentials.

**Technical Notes**:
- Configure Spring Security to permit `/api/accounts` (public) but secure `/api/tasks`.
</details>

---
<!--
### E2 Task Management
_Enable users to create, list, and filter tasks._

#### User Story 03: Create a Task
<details>

<summary>
<strong>As a</strong> registered user,
<strong>I want to</strong> create tasks with a title and description,
<strong>So that</strong> I can track my work.
</summary>

**Acceptance Criteria**:

- POST `/api/tasks` accepts JSON:
```json
{ "title": "Task 1", "description": "Do something" }
```
- Returns `200 OK` with task details (including auto-generated `id` and `status=CREATED`).
- Validates:
  - `400` if title/description is blank.
  - `401` if unauthenticated.
- Saves task to H2 with author = authenticated user’s email (lowercase).

**Technical Notes**:
- Use @NotBlank for validation.
- Map author to User entity (JPA @ManyToOne).
</details>

---
-->

## Tasks
### Task 01: Implement H2 Database Setup
- Add H2 dependencies.
- Configure `application.properties` for file-based storage.
- Ensure tables auto-update via `spring.jpa.hibernate.ddl-auto=update`.

### Task 02: Configure Spring Security
- Disable CSRF and sessions (stateless).
- Expose `/error`, `/actuator/shutdown`, and `/h2-console/**` publicly.
- Secure `/api/tasks` with Basic Auth.

### Task 03: Create User Entity and Repository
- Define `User` class with `id`, `email` (unique), and `password` fields.
- Implement `UserRepository` with `findByEmailIgnoreCase()`.

### Task 04: Build Registration Endpoint
- Create `AccountController` with `POST /api/accounts`.
- Validate request body (use DTO).
- Return appropriate HTTP status codes.

### Task 05: Test Edge Cases
- Test with:
  - Duplicate emails (mixed case).
  - Short passwords.
  - Missing fields.
- Verify H2 persistence after restart.

[<<](./README.md)
<!--
:%s/\(Sample \(Input\|Output\) \d:\)\n\(.*\)/```\r\r**\1**\r```\3/gc
<details>
<summary></summary>

## 
### description

### solution

</details>
-->

