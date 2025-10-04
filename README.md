# Payment Management System

A Java-based console application for managing employee payments, departments, and agent operations within an organization.

## 📋 Overview

This system provides a complete solution for managing payments across different departments with role-based access control. It supports three types of users: Directors, Managers, and Employees, each with specific permissions and capabilities.

## ✨ Features

### Role-Based Access Control
- **Director**: Full system access, department management, manager oversight, and comprehensive statistics
- **Manager**: Department-level management, employee supervision, payment processing
- **Employee**: View personal payment history and statistics

### Core Functionalities
- 🔐 Secure authentication with password hashing (Password4j)
- 👥 Agent management (create, update, deactivate employees and managers)
- 🏢 Department management
- 💰 Payment processing with multiple payment types (SALARY, BONUS, COMMISSION)
- 📊 Comprehensive statistics and reporting
- 🔍 Payment filtering by type and date range
- 📈 Agent ranking by total payments received

## 🏗️ Architecture

The application follows a **layered architecture**:

```
View Layer (Console UI)
    ↓
Controller Layer
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access Abstraction)
    ↓
DAO Layer (Database Operations)
    ↓
Database (MySQL)
```

### Design Patterns Used
- **Singleton Pattern**: Database connection management
- **Repository Pattern**: Data access abstraction
- **DAO Pattern**: Database operations
- **MVC Pattern**: Separation of concerns

## 🛠️ Technology Stack

- **Java 17**
- **MySQL 8.0**
- **Maven** (Build tool)
- **Password4j 1.8.4** (Password hashing)
- **MySQL Connector 8.0.33**
- **SLF4J 2.0.12** (Logging)

## 📦 Project Structure

```
src/
├── main/
│   ├── java/com/paymentmanagement/
│   │   ├── Main.java
│   │   ├── config/          # Database configuration
│   │   ├── controller/      # Controllers
│   │   ├── dao/             # Data Access Objects
│   │   ├── exception/       # Custom exceptions
│   │   ├── model/           # Entity models
│   │   ├── repository/      # Repository layer
│   │   ├── service/         # Business logic
│   │   ├── util/            # Utility classes
│   │   ├── validation/      # Input validation
│   │   └── view/            # Console UI
│   └── resources/
│       ├── database.properties
│       └── sql/
│           └── create-tables.sql
└── test/
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/AymanElh/PaymentManagementSystem.git
   cd PaymentManagementSystem
   ```

2. **Set up the database**
   ```bash
   mysql -u root -p < src/main/resources/sql/create-tables.sql
   ```

3. **Configure database connection**
   
   Edit `src/main/resources/database.properties`:
   ```properties
   db.url=jdbc:mysql://localhost:3306/payment_management_system
   db.username=your_username
   db.password=your_password
   ```


## 📊 Database Schema

### Main Tables
- **users**: Base user information
- **agents**: Extended user information with role and department
- **departments**: Department details
- **payments**: Payment records with type and amount

### Agent Types
- `DIRECTOR`
- `MANAGER`
- `EMPLOYEE`

### Payment Types
- `SALARY`
- `BONUS`
- `COMMISSION`

## 🎯 Usage Examples

### First Time Login
The system requires initial setup with a director account. Follow the on-screen prompts to create the first director user.

### Director Operations
- Create and manage departments
- Assign managers to departments
- View system-wide statistics
- Access comprehensive payment reports
- Rank agents by total payments

### Manager Operations
- Add employees to their department
- Process payments for employees
- View department statistics
- Deactivate employees from the department

### Employee Operations
- View payment history
- Filter payments by type
- View personal statistics (min/max payments, total received)

## 🔒 Security Features

- Password hashing using Password4j with Argon2 algorithm
- Session management with LoginSession
- Role-based access control
- Input validation to prevent SQL injection

## 📈 Statistics & Reporting

- Total payments by department
- Agent ranking by total payments received
- Payment history filtering
- Min/Max payment analysis
- Payment type breakdown

## 🐛 Error Handling

The system includes custom exceptions for:
- `EntityNotFoundException`: When entities are not found
- `AgentNotFoundException`: Specific to agent lookup failures
- `ValidationException`: Input validation errors
- `InvalidPaymentAmountException`: Payment amount validation

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📝 License

This project is developed for educational purposes.
