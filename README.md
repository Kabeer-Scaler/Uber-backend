# 🚕 Ride Sharing Backend (Spring Boot + MongoDB + JWT)

A fully functional Uber-style Ride Sharing Backend built using **Spring Boot 4**, **MongoDB**, **Spring Security 6**, **JWT-based authentication**, and **Clean Architecture**.

This project demonstrates how to build a professional-grade backend system with scalable architecture.

## 📌 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Folder Structure](#-folder-structure)
- [Data Models](#-data-models)
- [API Endpoints](#-api-endpoints)
- [Authentication & Security](#-authentication-jwt)
- [Global Exception Handling](#-global-exception-handling)
- [Setup Instructions](#-setup-instructions)
- [Postman Usage](#-postman-usage)

---

## 🌟 Overview
This backend mimics core features of a ride-sharing platform like Uber.
* **Users** can request rides.
* **Drivers** can accept rides.
* **Rides** can be completed and tracked.
* **JWT** ensures secure access for all endpoints.
* **MongoDB** is used for flexible data storage.
* **Clean Architecture** ensures the codebase is scalable and readable.

---

## 🚀 Features

### 🔐 Authentication & Roles
* Register/Login with **JWT (JSON Web Tokens)**.
* **BCrypt** password hashing for security.
* **Role-Based Access Control (RBAC):**
    * `ROLE_USER` → Passenger
    * `ROLE_DRIVER` → Driver

### 🚕 Ride Workflow
* Passenger requests a ride.
* Driver sees a list of pending rides.
* Driver accepts a ride.
* User/Driver marks the ride as complete.
* User can view their ride history.

### ✔ Additional Features
* **DTO-level validation** (Data Transfer Objects).
* **Centralized Exception Handling** (`@ControllerAdvice`).
* **Layered Architecture** (Controller, Service, Repository).
* **MongoDB Compass** support (Cloud Database).

---

## 🛠 Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Backend Framework** | Spring Boot 4 |
| **Language** | Java 21 |
| **Security** | Spring Security 6 + JWT |
| **Database** | MongoDB (Atlas or Local) |
| **Build Tool** | Maven |
| **Testing** | Postman |

---

## 🧱 Architecture

The project follows a **Clean 3-Layer Architecture**:

`Controller` → `Service` → `Repository` → `MongoDB`

### Security Flow
`Request` → `JwtAuthenticationFilter` → `SecurityConfig` → `Controller`

---

## 📂 Folder Structure

```bash
src/main/java/com/Kabeer/Uber
│
├── config/        # Security configurations + JWT filter
├── controller/    # REST API Controllers
├── dto/           # Data Transfer Objects (Request/Response)
├── exception/     # Global exception handlers
├── model/         # MongoDB Entities
├── repository/    # MongoRepository Interfaces
├── service/       # Business Logic Service Classes
└── util/          # JWT Utilities


## 🧾 Data Models

🧑 User Model
JSON

{
  "id": "string",
  "username": "string",
  "password": "bcrypt-hash",
  "role": "ROLE_USER or ROLE_DRIVER"
}
🚗 Ride Model
JSON

{
  "id": "string",
  "userId": "string",
  "driverId": "string or null",
  "pickupLocation": "string",
  "dropLocation": "string",
  "status": "REQUESTED / ACCEPTED / COMPLETED",
  "createdAt": "Date"
}
📡 API Endpoints
🔐 Authentication
Register User/Driver
POST /api/auth/register

JSON

{
  "username": "john",
  "password": "1234",
  "role": "ROLE_USER"
}
Login
POST /api/auth/login

JSON

{
  "username": "john",
  "password": "1234"
}
Response:

JSON

{ "token": "eyJhbG..." }
🚕 Passenger Endpoints
Request a Ride
POST /api/v1/rides

JSON

{
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar"
}
View My Rides
GET /api/v1/user/rides

🚗 Driver Endpoints
View All Pending Requests
GET /api/v1/driver/rides/requests

Accept a Ride
POST /api/v1/driver/rides/{rideId}/accept

🏁 General
Complete a Ride
POST /api/v1/rides/{rideId}/complete

🔐 Authentication (JWT)
All protected endpoints require the Authorization header:

Authorization: Bearer <your_token_here>

The JWT contains:

username

role

expiration

issuedAt

⚠️ Global Exception Handling
Error responses follow a consistent format:

JSON

{
  "error": "VALIDATION_ERROR",
  "message": "Pickup location is required",
  "timestamp": "2025-01-20T12:00:00Z"
}
Exceptions Handled:

Validation Errors

Resource Not Found

Bad Credentials

Internal Server Errors

🧪 Postman Usage
Register Passenger (/api/auth/register with ROLE_USER)

Register Driver (/api/auth/register with ROLE_DRIVER)

Login (/api/auth/login) -> Copy the Token

Set Authorization Header:

Go to the "Authorization" tab in Postman.

Select Type: Bearer Token.

Paste the token.

Test Routes:

Create a ride request.

Switch to Driver token -> Accept the ride.

Complete the ride.

🛠 Setup Instructions
1️⃣ Clone Repository
Bash

git clone [https://github.com/yourusername/uber-backend.git](https://github.com/yourusername/uber-backend.git)
2️⃣ Configure MongoDB
Create src/main/resources/application.yml. (Note: This file is ignored in .gitignore for security)

YAML

spring:
  application:
    name: Uber
  data:
    mongodb:
      # Use Localhost or Atlas URI
      uri: mongodb://localhost:27017/uber_db
      # uri: mongodb+srv://<username>:<password>@cluster0.mongodb.net/uber_db
3️⃣ Run the Application
Bash

mvn clean install
mvn spring-boot:run
The server will start on: http://localhost:8081
