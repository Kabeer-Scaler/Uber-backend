🚕 Ride Sharing Backend (Spring Boot + MongoDB + JWT)

A fully functional Uber-style Ride Sharing Backend built using Spring Boot 4, MongoDB, Spring Security 6, JWT-based authentication, and Clean Architecture.

This project supports:

👤 User registration & login

🧑‍🤝‍🧑 Roles: User (Passenger) & Driver

🚕 Ride request flow

🚗 Driver ride acceptance

✅ Ride completion

🔐 Token-based security

Ideal for learning how professional backend systems are structured.

📌 Table of Contents

Overview

Features

Tech Stack

Architecture

Folder Structure

Data Models

API Endpoints

Authentication (JWT)

Global Exception Handling

Postman Usage

Setup Instructions

Future Enhancements

License

🌟 Overview

This backend mimics core features of a ride-sharing platform like Uber:

Users can request rides

Drivers can accept rides

Rides can be completed

JWT ensures secure access

MongoDB stores users & rides

Clean architecture ensures scalability & readability

🚀 Features
🔐 Authentication & Roles

Register/Login with JWT

BCrypt password hashing

Roles:

ROLE_USER → Passenger

ROLE_DRIVER → Driver

🚕 Ride Workflow

Passenger requests a ride

Driver sees pending rides

Driver accepts a ride

User/Driver completes ride

User can view ride history

✔ Additional Features

DTO-level validation

Centralized exception handling

Layered architecture

MongoDB Atlas support

🛠 Tech Stack
Layer	Technology
Backend Framework	Spring Boot 4
Language	Java 24
Security	Spring Security 6 + JWT

🧱 Architecture
Clean 3-Layer Architecture
Controller → Service → Repository → MongoDB

Security Flow
Request → JwtAuthenticationFilter → SecurityConfig → Controller

📂 Folder Structure
src/main/java/com/Kabeer/Uber
│
├── config/            # Security + JWT filter
├── controller/        # REST APIs
├── dto/               # Request/Response DTOs
├── exception/         # Global exception handler
├── model/             # MongoDB entities
├── repository/        # MongoRepository interfaces
├── service/           # Business logic
└── util/              # JWT utilities

🧾 Data Models
🧑 User Model
{
  "id": "string",
  "username": "string",
  "password": "bcrypt-hash",
  "role": "ROLE_USER or ROLE_DRIVER"
}

🚗 Ride Model
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

Body:

{
  "username": "john",
  "password": "1234",
  "role": "ROLE_USER"
}

Login

POST /api/auth/login

Body:

{
  "username": "john",
  "password": "1234"
}


Response:

{
  "token": "eyJhbG..."
}

🚕 Passenger Endpoints
Request a Ride

POST /api/v1/rides

{
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar"
}

View My Rides

GET /api/v1/user/rides

🚗 Driver Endpoints
View All Pending Ride Requests

GET /api/v1/driver/rides/requests

Accept a Ride

POST /api/v1/driver/rides/{rideId}/accept

🏁 Complete a Ride

POST /api/v1/rides/{rideId}/complete

🔐 Authentication (JWT)

All protected endpoints require:

Authorization: Bearer <token>


JWT Contains:

username

role

expiry

issuedAt

⚠️ Global Exception Handling

Error responses follow a consistent format:

{
  "error": "VALIDATION_ERROR",
  "message": "Pickup is required",
  "timestamp": "2025-01-20T12:00:00Z"
}


Exceptions handled:

Validation errors

Not found

Bad request

Internal server errors

🧪 Postman Usage
1️⃣ Register Passenger
2️⃣ Register Driver
3️⃣ Login → Get JWT
4️⃣ Set Authorization Header

Go to Authorization → Select Bearer Token

5️⃣ Test routes:

Request ride

Accept ride

Complete ride

View ride history

🛠 Setup Instructions
1️⃣ Clone Repository
git clone https://github.com/<your-username>/<repo-name>.git

2️⃣ Configure MongoDB

Create src/main/resources/application.yml:

spring:
  application:
    name: Uber

  data:
    mongodb:
      uri: YOUR_MONGO_DB_ATLAS_URI


Note: This file is ignored in .gitignore for security.

3️⃣ Install Dependencies & Run
mvn clean install
mvn spring-boot:run


Server runs on:

http://localhost:8081
Persistence	MongoDB Atlas
Build Tool	Maven
Testing	Postman
