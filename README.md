# DineFlow — Restaurant Management System

DineFlow is a backend-focused Restaurant Management System built with **Java Spring Boot**, designed to handle real-world restaurant operations such as menu management, order processing, table reservations, billing, and reporting.

This project is being built incrementally, phase by phase, following production-level practices: layered architecture (Controller → Service → Repository), DTO-based API design, centralized exception handling, and Git-based feature branch workflow.

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
- [x] **Phase 2 — Order Management:** RestaurantTable, Order, OrderItem — order creation, status lifecycle, tax/total calculation, active orders view
- [ ] **Phase 3 — Authentication & Roles** (Spring Security + JWT) — *up next*
- [ ] Phase 4 — Table & Reservation Management
- [ ] Phase 5 — Billing & Payment
- [ ] Phase 6 — Kitchen Display / Order Workflow
- [ ] Phase 7 — Reports & Analytics
- [ ] Phase 8 — Advanced (Caching, File Upload, Notifications, Testing, Docker)

## API Endpoints

### Phase 1 — Category

| Method | Endpoint              | Description                  |
|--------|------------------------|-------------------------------|
| POST   | /api/categories         | Create a new category         |
| GET    | /api/categories         | Get all categories            |
| GET    | /api/categories/{id}    | Get a single category by ID   |
| PUT    | /api/categories/{id}    | Update a category by ID       |
| DELETE | /api/categories/{id}    | Delete a category by ID       |

### Phase 1 — MenuItem

| Method | Endpoint                          | Description                                  |
|--------|-------------------------------------|------------------------------------------------|
| POST   | /api/menu-items                     | Create a new menu item                        |
| GET    | /api/menu-items                     | Get all menu items                            |
| GET    | /api/menu-items/{id}                | Get a single menu item by ID                  |
| PUT    | /api/menu-items/{id}                | Update a menu item by ID                      |
| PATCH  | /api/menu-items/{id}/availability   | Toggle availability (AVAILABLE / OUT_OF_STOCK)|
| DELETE | /api/menu-items/{id}                | Delete a menu item by ID                      |

### Phase 2 — Order

| Method | Endpoint                    | Description                                              |
|--------|-------------------------------|-------------------------------------------------------------|
| POST   | /api/orders                   | Create a new order (with items, auto-calculates tax/total) |
| GET    | /api/orders                   | Get all orders (with nested items and menu items)          |
| GET    | /api/orders/{id}               | Get a single order by ID                                  |
| GET    | /api/orders/active              | Get all active orders (PLACED / IN_PROGRESS / SERVED)      |
| PATCH  | /api/orders/{id}/status         | Update order status                                        |

## Key Design Decisions

- **DTO Pattern:** Entities are never exposed directly through the API. Every module has separate Request/Response DTOs, mapped via dedicated Mapper classes.
- **Price Snapshot:** `OrderItem.unitPrice` stores the menu item's price at the time of order, so historical orders remain accurate even if menu prices change later.
- **Centralized Exception Handling:** A `GlobalExceptionHandler` handles not-found, validation, duplicate, and malformed request errors consistently across all modules.
- **Configurable Tax Rate:** Tax percentage is externalized via `application.properties` (`app.tax.percentage`) instead of hardcoded, using `@ConfigurationProperties`.


## Status
🚧 Actively in development — **Phase 2 (Order Management) complete.** Phase 3 (Authentication & Roles) in progress next.
