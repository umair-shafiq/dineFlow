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
- [x] **Phase 4 — Table & Reservation Management:** Table availability tracking tied to order lifecycle, Reservation CRUD with double-booking prevention
- [x] **Phase 5 — Billing & Payment:** Invoice generation, payment recording, receipt-ready nested data
- [x] **Phase 6 — Kitchen Display / Order Workflow:** Chef role, item-level cooking status, kitchen-facing order view
- [ ] **Phase 7 — Reports & Analytics** — *up next*
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

### Phase 4 — Restaurant Table

| Method | Endpoint              | Access | Description                          |
|--------|------------------------|--------|-----------------------------------------|
| POST   | /api/tables             | Admin  | Create a new table                    |
| GET    | /api/tables             | Admin  | Get all tables                        |
| GET    | /api/tables/{id}         | Admin  | Get a single table by ID              |
| PUT    | /api/tables/{id}         | Admin  | Update table (number, capacity, status) |
| DELETE | /api/tables/{id}         | Admin  | Delete a table                        |

### Phase 4 — Reservation

| Method | Endpoint                        | Access | Description                                              |
|--------|------------------------------------|--------|--------------------------------------------------------------|
| POST   | /api/reservations                   | Admin  | Create a reservation (checks for double-booking conflicts)  |
| GET    | /api/reservations                   | Admin  | Get all reservations                                        |
| GET    | /api/reservations/{id}               | Admin  | Get a single reservation by ID                              |
| PUT    | /api/reservations/{id}               | Admin  | Update reservation (table/time/guests/contact — partial update, re-checks conflicts) |
| PATCH  | /api/reservations/{id}/status        | Admin  | Update reservation status (PENDING / CONFIRMED / CANCELLED) |
| DELETE | /api/reservations/{id}               | Admin  | Delete a reservation                                         |

### Phase 5 — Invoice & Payment

| Method | Endpoint                        | Access         | Description                                        |
|--------|------------------------------------|----------------|-------------------------------------------------------|
| GET    | /api/invoices                       | Admin, Waiter  | Get all invoices                                     |
| GET    | /api/invoices/{id}                   | Admin, Waiter  | Get a single invoice — full receipt-ready nested data |
| POST   | /api/invoices/{id}/payment           | Admin, Waiter  | Record a payment, marks invoice PAID                 |
| GET    | /api/invoices/{id}/payment           | Admin, Waiter  | Get payment details for an invoice                   |

### Phase 6 — Kitchen

| Method | Endpoint                              | Access       | Description                                              |
|--------|------------------------------------------|--------------|----------------------------------------------------------------|
| GET    | /api/kitchen/orders                       | Admin, Chef  | Get active orders (PLACED/IN_PROGRESS) with item-level status  |
| PATCH  | /api/kitchen/order-items/{id}/status       | Admin, Chef  | Update an order item's cooking status (PENDING/COOKING/READY)  |

## Key Design Decisions

- **DTO Pattern:** Entities are never exposed directly through the API. Every module has separate Request/Response DTOs,
  mapped via dedicated Mapper classes.
- **Price Snapshot:** `OrderItem.unitPrice` stores the menu item's price at the time of order, so historical orders
  remain accurate even if menu prices change later.
- **POS Architecture:** This system is designed for staff use (Admin, Waiter) inside the restaurant — not as a
  customer-facing online ordering platform. Orders can be Dine-in (linked to a RestaurantTable) or Takeaway (no table).
- **Table Lifecycle Integration:** Table status (`FREE`/`OCCUPIED`/`RESERVED`) is automatically managed by the Order lifecycle — no manual staff intervention needed to free a table after an order completes.
- **Reservation vs. Live Occupancy:** Reservations represent *future* bookings and are validated independently of a table's current live status, using a time-window overlap check (not the table's real-time `FREE`/`OCCUPIED` state) to prevent double-booking.
- **Invoice Generation is Manual, Not Status-Triggered:** Invoices are generated on-demand via a dedicated endpoint rather than automatically tied to a specific order status, since real restaurants bill at different points in the order lifecycle (before preparation for quick-service, after serving for dine-in).
- **Item-Level Kitchen Status:** Cooking progress is tracked per `OrderItem`, not per `Order` — since different items in the same order finish cooking at different times. Order-level status (Phase 2) and item-level status (Phase 6) are intentionally separate concerns.
- **Audience-Specific Response DTOs:** The same underlying Order/OrderItem data is exposed differently depending on the consumer — e.g., Kitchen DTOs omit pricing and category data that a Chef doesn't need, while Waiter/Admin views include full financial details.
- **Polling Before WebSocket:** Near-real-time updates (kitchen display, waiter notification of ready items) are implemented via simple periodic refresh (polling) for now; WebSocket-based push notifications are deferred to a later phase as a deliberate scoping decision, not an oversight.
- **Printing is a Frontend/Hardware Concern:** The backend returns structured, receipt-ready invoice data; formatting for thermal printers and triggering print happens client-side.
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

🚧 Actively in development — **Phase 6 (Kitchen Display / Order Workflow) complete.** Phase 7 (Reports & Analytics) next.