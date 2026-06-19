# Real Estate Platform — Documentation

## Overview

A full-stack web application for browsing, creating, and managing real estate listings (apartments and houses for sale or rent). Users can register, post listings with photos, filter and sort results, and send inquiries to listing owners.

---

## Architecture

```
real-estate/
├── src/                        # Spring Boot backend (Java 25)
│   └── main/java/com/thedevs/real_estate/
│       ├── controller/         # REST controllers
│       ├── service/            # Business logic
│       ├── repository/         # Spring Data JPA
│       ├── model/              # JPA entities
│       ├── dto/                # Request/response DTOs
│       └── security/           # JWT auth filters
├── frontend/                   # Vue 3 SPA
│   └── src/
│       ├── components/         # Vue components
│       ├── router/             # Vue Router (7 routes)
│       ├── stores/             # Pinia state (auth)
│       └── api/                # Axios HTTP clients
├── compose.yaml                # Docker Compose
└── Dockerfile                  # Multi-stage build
```

**Backend:** Spring Boot 4.0.6 · Java 25 · PostgreSQL · Spring Security + JWT · Cloudinary (image uploads)

**Frontend:** Vue 3 · Vite · Pinia · CSS · Axios

**Deployment:** Docker multi-stage build (JDK → JRE), exposed on port `8080`

### Data Model

| Entity | Key Fields |
|---|---|
| `User` | username, email, password (BCrypt), role (USER/ADMIN) |
| `Property` | address, size, owner (User) — base for Apartment/House |
| `Apartment` | extends Property: rooms, floor, buildingYear, furnished |
| `House` | extends Property: garage, landSize, stories |
| `Listing` | property, type (SALE/RENT), price, status (ACTIVE/INACTIVE) |
| `Sale` | listing, buyer, saleDate, price |
| `Rental` | listing, tenant, rentPrice, leaseStart, leaseEnd |
| `Inquiry` | sender, receiver, listing, message, status (READ/UNREAD) |
| `PropertyPhoto` | property, cloudinaryUrl |

---

## Setup

### Prerequisites

- Java 25 (JDK)
- Node.js 20.19+ or 22.12+
- PostgreSQL
- Cloudinary account

### Environment Variables

Copy `.env.example` to `.env` in the project root and fill in:

```env
DB_URL=jdbc:postgresql://localhost:5432/real_estate_db
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
CLOUDINARY_CLOUD_NAME=your_cloudinary_cloud_name
CLOUDINARY_API_KEY=your_cloudinary_api_key
CLOUDINARY_API_SECRET=your_cloudinary_api_secret
```

### Local Development

```bash
# Backend (from project root)
./mvnw spring-boot:run

# Frontend (from frontend/)
npm install
npm run dev
```

Frontend dev server: `http://localhost:5173`  
Backend API: `http://localhost:8080`

### Docker

```bash
docker compose up --build
# App available at http://localhost:8080
```

> The compose file expects PostgreSQL running on the host. To containerize the database, uncomment the `db` service in `compose.yaml`.

---

## API Documentation

Base URL: `/api`

All protected routes require `Authorization: Bearer <token>` header.

---

### Authentication

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | No | Register a new user |
| `POST` | `/api/auth/login` | No | Login and receive JWT |

**Register request:**
```json
{ "username": "string", "email": "string", "password": "string" }
```

**Login response:**
```json
{ "token": "eyJ..." }
```

---

### Listings

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/api/listings` | Yes | List listings (paginated, filterable, sortable) |
| `GET` | `/api/listings/{id}` | Yes | Get listing by ID |
| `POST` | `/api/listings` | Yes | Create listing |
| `PUT` | `/api/listings/{id}` | Yes | Update listing |
| `DELETE` | `/api/listings/{id}` | Yes | Delete listing |

**GET `/api/listings` — Query Parameters:**

| Parameter | Type | Description |
|---|---|---|
| `page` | int | Page number (0-based) |
| `size` | int | Page size |
| `userId` | long | Filter by owner ID |
| `address` | string | Filter by address (partial match) |
| `maxPrice` | decimal | Maximum price |
| `minRooms` | int | Minimum number of rooms |
| `listingType` | string | `SALE` or `RENT` |
| `sortBy` | string | Field to sort by: `id` (default), `price`, `numberOfRooms` |
| `sortDir` | string | `asc` (default) or `desc` |

The `sortBy` and `sortDir` parameters map directly to the `ListingSort.vue` component, which emits `{ sortBy, sortDir }` on user interaction.

---

### Apartments

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/api/apartments` | Yes | List all apartments |
| `GET` | `/api/apartments/{id}` | Yes | Get apartment by ID |
| `POST` | `/api/apartments` | Yes | Create apartment |
| `PUT` | `/api/apartments/{id}` | Yes | Update apartment |
| `DELETE` | `/api/apartments/{id}` | Yes | Delete apartment |

---

### Houses

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/api/houses` | Yes | List all houses |
| `GET` | `/api/houses/{id}` | Yes | Get house by ID |
| `POST` | `/api/houses` | Yes | Create house |
| `PUT` | `/api/houses/{id}` | Yes | Update house |
| `DELETE` | `/api/houses/{id}` | Yes | Delete house |

---

### Property Photos

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/api/properties/{propertyId}/photos` | Yes | Upload photo (multipart/form-data, max 10 MB) |
| `DELETE` | `/api/properties/{propertyId}/photos/{photoId}` | Yes | Delete photo |

---

### Inquiries

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/api/inquiries` | Yes | Send inquiry about a listing |
| `GET` | `/api/inquiries/received` | Yes | Get inquiries received by the authenticated user |
| `GET` | `/api/inquiries/unread-count` | Yes | Count of unread inquiries |
| `PUT` | `/api/inquiries/{id}/read` | Yes | Mark inquiry as read |

---

### Sales & Rentals

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET/POST` | `/api/sales` | Yes | List / create sales |
| `GET/PUT/DELETE` | `/api/sales/{id}` | Yes | Read / update / delete sale |
| `GET/POST` | `/api/rentals` | Yes | List / create rentals |
| `GET/PUT/DELETE` | `/api/rentals/{id}` | Yes | Read / update / delete rental |

---

### Users

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/api/users` | Yes | List all users |
| `GET` | `/api/users/{id}` | Yes | Get user by ID |
| `POST` | `/api/users` | Yes | Create user |
| `PUT` | `/api/users/{id}` | Yes | Update user |

---

## Frontend Routes

| Path | Page | Description |
|---|---|---|
| `/auth` | AuthPage | Login / Register (guest only) |
| `/` | ListingPage | Browse and filter listings |
| `/listings/create` | ListingCreatePage | Create a new listing |
| `/listings/:id` | ListingDetailPage | View listing details |
| `/listings/:id/edit` | ListingEditPage | Edit a listing |
| `/profile` | ProfilePage | User profile and own listings |
| `/inquiries` | InquiriesPage | Received inquiries |

All routes except `/auth` require authentication. Unauthenticated users are redirected to `/auth`.
