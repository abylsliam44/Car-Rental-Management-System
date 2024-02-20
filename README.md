# Car Rental Management System

This project is a Java-based car rental management system. It allows users to perform various operations related to car rental, including adding new users, listing all users, adding new vehicles, updating vehicle status, listing all vehicles, starting the rental process, calculating total profit, and calculating rent cost.

## Features

- **Add New User**: Users can register new customers by providing their full name, email, phone number, and address.
- **List All Users**: The system provides the functionality to display a list of all registered users.
- **Add New Vehicle**: Users can add new vehicles to the system by providing details such as license plate, make, model, year, and daily rental price.
- **Update Vehicle Status**: The system allows updating the status of vehicles, such as marking them as available, rented, etc.
- **List All Vehicles**: Users can view a list of all vehicles registered in the system.
- **Start Rental Process**: Customers can rent a car by selecting a vehicle, providing rental start and end dates and times.
- **Calculate Total Profit**: The system calculates the total profit earned from all rentals.
- **Calculate Rent Cost**: Users can calculate the cost of renting a vehicle based on the start and end dates and times.

## Technologies Used

- Java
- MySQL
- JDBC

## Setup

1. Clone the repository.
2. Set up the MySQL database using the provided SQL script.
3. Update the database connection details in the `DatabaseConnection.java` file.
4. Import the project into IntelliJ IDEA.
5. Run the `Main.java` file to start the application.

## Database Schema

The project uses the following database schema:

### Customers Table

```sql
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    PhoneNumber VARCHAR(20) NOT NULL,
    Address VARCHAR(255) NOT NULL
);
CREATE TABLE Vehicles (
    VehicleID INT AUTO_INCREMENT PRIMARY KEY,
    LicensePlate VARCHAR(20) NOT NULL,
    Make VARCHAR(50) NOT NULL,
    Model VARCHAR(50) NOT NULL,
    Year INT NOT NULL,
    Status VARCHAR(20) NOT NULL,
    DailyRentalPrice DECIMAL(10, 2) NOT NULL
);
CREATE TABLE Rentals (
    RentalID INT PRIMARY KEY,
    VehicleID INT,
    CustomerID INT,
    RentalDate DATETIME,
    DueDate DATETIME,
    ReturnDate DATETIME,
    DailyRate DECIMAL(10, 2),
    TotalAmount DECIMAL(10, 2),
    Status VARCHAR(50)
);
