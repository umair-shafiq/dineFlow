# DineFlow — Restaurant Management System

DineFlow is a backend-focused Restaurant Management System built with **Java Spring Boot**, designed to handle
real-world restaurant operations such as menu management, order processing, table reservations, billing, and reporting.

This project is being built incrementally, phase by phase, following production-level practices: layered architecture (
Controller → Service → Repository), DTO-based API design, centralized exception handling, and Git-based feature branch
workflow.

## Tech Stack

- **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security (upcoming)
- **Database:** MySQL
- **Build Tool:** Maven
- **API Testing:** Postman
- **Version Control:** Git & GitHub

## Architecture

Controller → Service → Repository → Database
↕
DTO (Request/Response)

## Project Roadmap

This backend is being built in **8 total phases**. Current status: **Phase 2 complete, Phase 3 next.**

- [x] **Phase 1 — Menu Management:** Category CRUD, MenuItem CRUD (with availability status)
- [x] **Phase 2 — Order Management:** RestaurantTable, Order, OrderItem — order creation, status lifecycle, tax/total
  calculation, active orders view
- [x] **Phase 3 — Authentication & Roles:** Spring Security + JWT, User management, role-based access (Admin / Waiter)
- [ ] **Phase 4 — Table & Reservation Management** — *up next*
- [ ] Phase 5 — Billing & Payment
- [ ] Phase 6 — Kitchen Display / Order Workflow
- [ ] Phase 7 — Reports & Analytics
- [ ] Phase 8 — Advanced (Caching, File Upload, Notifications, Testing, Docker)

## API Endpoints

### Phase 1 — Category

| Method | Endpoint             | Description                 |
|--------|----------------------|-----------------------------|
| POST   | /api/categories      | Create a new category       |
| GET    | /api/categories      | Get all categories          |
| GET    | /api/categories/{id} | Get a single category by ID |
| PUT    | /api/categories/{id} | Update a category by ID     |
| DELETE | /api/categories/{id} | Delete a category by ID     |

### Phase 1 — MenuItem

| Method | Endpoint                          | Description                                    |
|--------|-----------------------------------|------------------------------------------------|
| POST   | /api/menu-items                   | Create a new menu item                         |
| GET    | /api/menu-items                   | Get all menu items                             |
| GET    | /api/menu-items/{id}              | Get a single menu item by ID                   |
| PUT    | /api/menu-items/{id}              | Update a menu item by ID                       |
| PATCH  | /api/menu-items/{id}/availability | Toggle availability (AVAILABLE / OUT_OF_STOCK) |
| DELETE | /api/menu-items/{id}              | Delete a menu item by ID                       |

### Phase 2 — Order

| Method | Endpoint                | Description                                                |
|--------|-------------------------|------------------------------------------------------------|
| POST   | /api/orders             | Create a new order (with items, auto-calculates tax/total) |
| GET    | /api/orders             | Get all orders (with nested items and menu items)          |
| GET    | /api/orders/{id}        | Get a single order by ID                                   |
| GET    | /api/orders/active      | Get all active orders (PLACED / IN_PROGRESS / SERVED)      |
| PATCH  | /api/orders/{id}/status | Update order status                                        |

### Phase 3 — Auth & Users

| Method | Endpoint                    | Access | Description                         |
|--------|-----------------------------|--------|-------------------------------------|
| POST   | /api/auth/login             | Public | Authenticate, returns JWT token     |
| POST   | /api/users                  | Admin  | Create a new user (Admin or Waiter) |
| GET    | /api/users                  | Admin  | List all users                      |
| GET    | /api/users/{id}             | Admin  | Get a single user by ID             |
| GET    | /api/users/search?email=... | Admin  | Get a user by email                 |
| PUT    | /api/users/{id}             | Admin  | Update user details                 |
| PATCH  | /api/users/{id}/status      | Admin  | Enable/disable a user account       |

## Key Design Decisions

- **DTO Pattern:** Entities are never exposed directly through the API. Every module has separate Request/Response DTOs,
  mapped via dedicated Mapper classes.
- **Price Snapshot:** `OrderItem.unitPrice` stores the menu item's price at the time of order, so historical orders
  remain accurate even if menu prices change later.
- **POS Architecture:** This system is designed for staff use (Admin, Waiter) inside the restaurant — not as a
  customer-facing online ordering platform. Orders can be Dine-in (linked to a RestaurantTable) or Takeaway (no table).
- **Centralized Exception Handling:** A `GlobalExceptionHandler` handles not-found, validation, duplicate, malformed
  request, and authentication errors consistently across all modules.
- **JWT Authentication:** Stateless authentication using signed JWT tokens. No server-side sessions. Tokens expire after
  24 hours.
- **Role-Based Authorization:** Access rules are centralized in `SecurityFilterChain` (URL-pattern based) rather than
  scattered `@PreAuthorize` annotations, keeping the entire security policy visible in one place.
- **Password Security:** Passwords are hashed using BCrypt and never stored or returned in plain text.
- **Bootstrap Problem Solved:** The first Admin account is automatically seeded on application startup, since no user
  can create the first Admin through the API (login-protected by design).
- **Configurable Tax Rate:** Tax percentage is externalized via `application.properties` (`app.tax.percentage`) instead
  of hardcoded, using `@ConfigurationProperties`.

## How to Run Locally

```bash
git clone https://github.com/<your-username>/dineflow.git
cd dineflow
```

Set the following in `application.properties` (or as environment variables):

```properties
app.tax.percentage=5
app.admin.email=admin@dineflow.com
app.admin.password=YourSecureAdminPassword
app.jwt.secret=YOUR_BASE64_SECRET_KEY
app.jwt.expiration=86400000
```

```bash
mvn spring-boot:run
```

Application runs on: `http://localhost:8080`

On first startup, a default Admin account is created automatically using the credentials above — use these to log in via
`POST /api/auth/login` and obtain a JWT for testing protected endpoints.

## Status

🚧 Actively in development — **Phase 3 (Authentication & Roles) complete.** Phase 4 (Table & Reservation Management)
next.