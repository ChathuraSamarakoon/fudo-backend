# 🍔 Fudo Backend

A RESTful API backend built with **Spring Boot** for a food ordering platform. This backend handles user authentication, product management, order processing, and customer messaging.

> 🎓 Built as part of a personal project to demonstrate backend development skills using Java and Spring Boot.

---

## 🚀 Tech Stack

| Technology | Details |
|---|---|
| **Language** | Java 17 |
| **Framework** | Spring Boot 4.0.6 |
| **ORM** | Spring Data JPA / Hibernate |
| **Database** | MySQL (hosted on Aiven Cloud) |
| **Build Tool** | Maven |
| **Utilities** | Lombok |

---

## 📁 Project Structure

```
fudo-backend/
├── src/
│   └── main/
│       ├── java/fudo_backend/
│       │   ├── controller/        # REST API endpoints
│       │   │   ├── UserController.java
│       │   │   ├── ProductController.java
│       │   │   ├── OrderController.java
│       │   │   └── MessageController.java
│       │   ├── model/             # JPA Entity classes
│       │   │   ├── User.java
│       │   │   ├── Product.java
│       │   │   ├── Order.java
│       │   │   ├── OrderItem.java
│       │   │   ├── Message.java
│       │   │   ├── OrderStatus.java
│       │   │   └── Role.java
│       │   ├── repository/        # Database access layer
│       │   │   ├── UserRepository.java
│       │   │   ├── ProductRepository.java
│       │   │   ├── OrderRepository.java
│       │   │   ├── OrderItemRepository.java
│       │   │   └── MessageRepository.java
│       │   ├── service/           # Business logic layer
│       │   │   ├── UserService.java
│       │   │   ├── ProductService.java
│       │   │   ├── OrderService.java
│       │   │   └── MessageService.java
│       │   └── FudoBackendApplication.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- Maven 3.x
- MySQL database

### 1. Clone the Repository

```bash
git clone https://github.com/ChathuraSamarakoon/fudo-backend.git
cd fudo-backend
```

### 2. Configure the Database

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://<host>:<port>/<database>
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
```

> ⚠️ **Security Note:** Never commit real credentials to version control. Use environment variables or a secrets manager in production.

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

The server starts on **http://localhost:8080**

---

## 📌 API Reference

### 👤 Users — `/api/users`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/users/register` | Register a new user |
| `POST` | `/api/users/login` | Login and authenticate |
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/email/{email}` | Find user by email |

**Register / Login Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securepassword"
}
```

**Login Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "CUSTOMER",
  "createdAt": "2026-06-01T10:00:00"
}
```

---

### 🍕 Products — `/api/products`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/products/{id}` | Get product by ID |
| `GET` | `/api/products/category/{category}` | Get products by category |
| `POST` | `/api/products` | Add a new product |
| `PUT` | `/api/products/{id}` | Update a product |
| `DELETE` | `/api/products/{id}` | Delete (soft-delete) a product |

**Request Body:**
```json
{
  "name": "Chicken Burger",
  "description": "Crispy chicken burger with lettuce and sauce",
  "price": 850.00,
  "imageUrl": "https://example.com/burger.jpg",
  "category": "Burgers",
  "isAvailable": true
}
```

---

### 📦 Orders — `/api/orders`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/orders` | Place a new order |
| `GET` | `/api/orders` | Get all orders |
| `GET` | `/api/orders/{id}` | Get order by ID |
| `GET` | `/api/orders/user/{userId}` | Get all orders by a user |
| `PATCH` | `/api/orders/{id}/status` | Update order status |

**Place Order Request Body:**
```json
{
  "order": {
    "user": { "id": 1 },
    "totalPrice": 0
  },
  "orderItems": [
    {
      "product": { "id": 3 },
      "quantity": 2,
      "price": 0
    }
  ]
}
```

> 💡 `totalPrice` and item `price` are automatically calculated server-side from the product catalog.

**Update Order Status Request Body:**
```json
{
  "status": "PREPARING"
}
```

**Order Status Values:**

| Status | Description |
|---|---|
| `PENDING` | Order placed, awaiting confirmation |
| `PREPARING` | Kitchen is preparing the order |
| `COMPLETED` | Order delivered successfully |
| `CANCELLED` | Order was cancelled |

---

### 💬 Messages — `/api/messages`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/messages` | Send a message |
| `GET` | `/api/messages` | Get all messages |
| `GET` | `/api/messages/{id}` | Get message by ID |
| `PUT` | `/api/messages/{id}/read` | Mark message as read |
| `DELETE` | `/api/messages/{id}` | Delete a message |

**Request Body:**
```json
{
  "name": "Jane",
  "email": "jane@example.com",
  "subject": "Order Inquiry",
  "message_text": "I wanted to ask about my recent order."
}
```

---

## 🏗️ Architecture

This project follows a clean **Layered Architecture**:

```
HTTP Request
     ↓
Controller Layer    →  Handles incoming API requests and responses
     ↓
Service Layer       →  Contains all business logic
     ↓
Repository Layer    →  Data access using Spring Data JPA
     ↓
MySQL Database
```

---

## 🔐 Roles & Permissions

| Role | Description |
|---|---|
| `CUSTOMER` | Default role assigned on registration |
| `ADMIN` | Has access to manage products and view all orders |

---

## 🗄️ Data Models

### User
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| name | String | Required |
| email | String | Unique |
| password | String | Required |
| role | Enum | `CUSTOMER` or `ADMIN` |
| createdAt | LocalDateTime | Auto-generated |

### Product
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| name | String | Required |
| description | String | Max 500 chars |
| price | Double | Required |
| imageUrl | String | Optional |
| category | String | Required |
| isAvailable | Boolean | Defaults to `true` |

### Order
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| user | User | Foreign key |
| totalPrice | Double | Calculated server-side |
| status | Enum | Defaults to `PENDING` |
| orderItems | List\<OrderItem\> | Cascaded |
| createdAt | LocalDateTime | Auto-generated |

### OrderItem
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| order | Order | Foreign key |
| product | Product | Foreign key |
| quantity | Integer | Required |
| price | Double | Fetched from product |

### Message
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| user | User | Optional (guests can message) |
| name | String | Sender's name |
| email | String | Sender's email |
| subject | String | Required |
| message_text | String | Max 1000 chars |
| isRead | Boolean | Defaults to `false` |
| createdAt | LocalDateTime | Auto-generated |

---

## 🔮 Future Improvements

- [ ] JWT-based authentication and authorization
- [ ] Password hashing with BCrypt
- [ ] Pagination and filtering for product/order lists
- [ ] Image upload support
- [ ] Swagger / OpenAPI documentation
- [ ] Unit and integration tests

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
