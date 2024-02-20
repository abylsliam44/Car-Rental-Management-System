import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private Scanner scanner;
    private RentCalculator rentCalculator;
    private Customer customer; // Add an instance of the Customer class

    public Main() {
        this.scanner = new Scanner(System.in);
        this.rentCalculator = new RentCalculator(10.0);
        this.customer = new Customer(); // Create an instance of the Customer class
    }

    public void start() {
        int option = 0;
        do {
            displayMenu();
            option = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (option) {
                case 1:
                    addNewUser(); // Call the method to add a new user
                    break;
                case 2:
                    listAllUsers();
                    break;
                case 3:
                    addNewVehicle();
                    break;
                case 4:
                    updateVehicleStatus();
                    break;
                case 5:
                    listAllVehicles();
                    break;
                case 6:
                    startRentalProcess(); // Call the method to start the rental process
                    break;
                case 7:
                    calculateTotalProfit();
                    break;
                case 8:
                    calculateRentCost();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 9);
    }

    private void displayMenu() {
        System.out.println("Car Rental Management System");
        System.out.println("1. Add New User");
        System.out.println("2. List All Users");
        System.out.println("3. Add New Vehicle");
        System.out.println("4. Update Vehicle Status");
        System.out.println("5. List All Vehicles");
        System.out.println("6. Start Rental Process");
        System.out.println("7. Calculate Total Profit");
        System.out.println("8. Calculate Rent Cost");
        System.out.println("9. Exit");
        System.out.print("Choose an option: ");
    }

    private void addNewUser() {
        System.out.println("Adding a new user to the system.");
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter Full Name: ");
            String fullName = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            String sql = "INSERT INTO Customers (FullName, Email, PhoneNumber, Address) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, fullName);
                pstmt.setString(2, email);
                pstmt.setString(3, phoneNumber);
                pstmt.setString(4, address);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("User added successfully!");
                } else {
                    System.out.println("Failed to add the user.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while adding a new user.");
            e.printStackTrace();
        }
    }

    private void listAllUsers() {
        System.out.println("List of all users:");
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Customers");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("CustomerID") + ", Full Name: " + rs.getString("FullName") +
                        ", Email: " + rs.getString("Email") + ", Phone Number: " + rs.getString("PhoneNumber") +
                        ", Address: " + rs.getString("Address"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while listing users.");
            e.printStackTrace();
        }
    }

    private void addNewVehicle() {
        System.out.println("Adding a new vehicle to the system.");
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter License Plate: ");
            String licensePlate = scanner.nextLine();

            System.out.print("Enter Make: ");
            String make = scanner.nextLine();

            System.out.print("Enter Model: ");
            String model = scanner.nextLine();

            System.out.print("Enter Year: ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Daily Rental Price: ");
            double dailyRentalPrice = Double.parseDouble(scanner.nextLine());

            String sql = "INSERT INTO Vehicles (LicensePlate, Make, Model, Year, Status, DailyRentalPrice) VALUES (?, ?, ?, ?, 'Available', ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, licensePlate);
                pstmt.setString(2, make);
                pstmt.setString(3, model);
                pstmt.setInt(4, year);
                pstmt.setDouble(5, dailyRentalPrice);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Vehicle added successfully!");
                } else {
                    System.out.println("Failed to add the vehicle.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while adding a new vehicle.");
            e.printStackTrace();
        }
    }

    private void updateVehicleStatus() {
        System.out.println("Updating vehicle status.");
        // Implement functionality to update the status of a vehicle
    }

    private void listAllVehicles() {
        System.out.println("List of all vehicles:");
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Vehicles");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Vehicle ID: " + rs.getInt("VehicleID") + ", License Plate: " + rs.getString("LicensePlate") +
                        ", Make: " + rs.getString("Make") + ", Model: " + rs.getString("Model") +
                        ", Year: " + rs.getInt("Year") + ", Status: " + rs.getString("Status") +
                        ", Daily Rental Price: " + rs.getDouble("DailyRentalPrice"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while listing vehicles.");
            e.printStackTrace();
        }
    }

    private void startRentalProcess() {
        customer.rentCar(); // Call the method to rent a car
    }

    private void calculateTotalProfit() {
        double totalProfit = 0.0;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Rentalss");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int vehicleId = rs.getInt("VehicleID");
                double dailyRate = rs.getDouble("DailyRate");
                int rentalDays = calculateRentalDays(rs.getDate("RentalDate"), rs.getDate("ReturnDate"));

                totalProfit += calculateProfitForRental(vehicleId, dailyRate, rentalDays);
            }
            System.out.println("Total Profit: $" + totalProfit);
        } catch (SQLException e) {
            System.out.println("Error occurred while calculating total profit.");
            e.printStackTrace();
        }
    }

    private int calculateRentalDays(java.sql.Date startDate, java.sql.Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        long millisPerDay = 24 * 60 * 60 * 1000;
        return (int) ((endDate.getTime() - startDate.getTime()) / millisPerDay);
    }

    private double calculateProfitForRental(int vehicleId, double dailyRate, int rentalDays) {
        double profit = 0.0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT DailyRentalPrice FROM Vehicles WHERE VehicleID = ?")) {
            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double dailyRentalPrice = rs.getDouble("DailyRentalPrice");
                profit = dailyRentalPrice * rentalDays;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while calculating profit for a rental.");
            e.printStackTrace();
        }
        return profit;
    }

    private void calculateRentCost() {
        System.out.println("Calculate Rent Cost");


        System.out.print("Enter start time (yyyy-MM-dd HH:mm): ");
        String startTimeStr = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.print("Enter end time (yyyy-MM-dd HH:mm): ");
        String endTimeStr = scanner.nextLine();
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


        double rentCost = rentCalculator.calculateRentCost(startTime, endTime);
        System.out.println("The total cost of rent is: $" + rentCost);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}
