# Task Management REST API tasks

- [Task Management REST API tasks](#task-management-rest-api-tasks)
  - [Epics](#epics)
  - [User Stories](#user-stories)
  - [Tasks](#tasks)

## Epics 
- [User Management](#e1-user-management)
- [Task Management](#e2-task-management)

## User Stories
- [US01: Register New User](#user-story-01-register-new-user)
- [US02: Secure Tasks Endpoint](#user-story-02-secure-tasks-endpoint)
- [US03: Create a Task](#user-story-03-create-a-task)
- [US04: List All Tasks](#user-story-04-list-all-tasks)
- [US05: Filter Tasks by Author](#user-story-05-filter-tasks-by-author)

## Tasks
- [Task01: Implement H2 Database Setup](#task-01-implement-h2-database-setup)
- [Task02: Configure Spring Security](#task-02-configure-spring-security)
- [Task03: Create User Entity and Repository](#task-03-create-user-entity-and-repository)
- [Task04: Build Registration Endpoint](#task-04-build-registration-endpoint)
- [Task05: Test Edge Cases](#task-05-test-edge-cases)
- [Task 06: Implement Task Entity](#task-06-implement-task-entity)
- [Task 07: Build Task Controller](#task-07-build-task-controller)
- [Task 08: Add Status Handling](#task-08-add-status-handling)
- [Task 09: Test Edge Cases](#task-09-test-edge-cases)
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

#### User Story 04: List All Tasks
<details>
<summary>
<strong>As a</strong> user,
<strong>I want to</strong> view all tasks (newest first),
<strong>So that</strong> I can see recent activity.
</summary>

**Acceptance Criteria**:
- GET `/api/tasks` returns `200 OK` with tasks sorted by newest first.
- Response format matches the JSON array example.
- Requires authentication (`401` otherwise).

**Technical Notes**:
- Use `ORDER BY createdAt DESC` in repository query.
</details>

#### User Story 05: Filter Tasks by Author
<details>
<summary>
<strong>As a</strong> user,
<strong>I want to</strong> filter tasks by author email (case-insensitive),
<strong>So that</strong> I can find specific users' tasks.
</summary>

**Acceptance Criteria**:
- GET `/api/tasks?author=user@mail.com` returns tasks by author.
- Email comparison is case-insensitive
- Returns empty array if no matches.

**Technical Notes**:
- Use `IgnoreCase` in repository method (e.g., `findByAuthorEmailIgnoreCase`).
</details>

<!--
#### User Story 00:
<details>
<summary>
<strong>As a</strong>
<strong>I want to</strong>
<strong>So that</strong>
</summary>

**Acceptance Criteria**:
- GET `/api/tasks`

**Technical Notes**:
- Use
</details>

-->
---

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

### Task 06: Implement Task Entity
- Define `Task` class with:
  - `id` (UUID/Long), `title`, `description`, `status` (enum), `author` (User).
  - `createdAt` timestamp for sorting.
- Add JPA annotations (`@Entity`, `@ManyToOne`).

### Task 07: Build Task Controller
- Create `TaskController` with:
  - POST `/api/tasks` (validates + saves task).
  - GET `/api/tasks` (list tasks, supports `author` filter).
- Secure endpoints with `@PreAuthorize`.

### Task 08: Add Status Handling
- Default `status` to `CREATED` on task creation.
- Use enum for status (`CREATED`, `IN_PROGRESS`, etc.).

### Task 09: Test Edge Cases
- Test:
  - Blank title/description (`400`).
  - Unauthenticated access (`401`).
  - Author filter (mixed-case emails).
  - H2 persistence after restart.

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

