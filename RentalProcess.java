import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RentalProcess {

    private Scanner scanner;

    public RentalProcess() {
        this.scanner = new Scanner(System.in);
    }

    public void startRentalProcess() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Starting rental process...");

            // Display available vehicles
            System.out.println("Available vehicles:");
            listAvailableVehicles(connection);

            // Get user input for vehicle selection
            System.out.print("Enter Vehicle ID to rent: ");
            int vehicleId = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            // Check if the selected vehicle is available
            if (!isVehicleAvailable(connection, vehicleId)) {
                System.out.println("Selected vehicle is not available for rental.");
                return;
            }

            // Get user input for customer details
            System.out.print("Enter Customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            System.out.print("Enter Daily Rate: ");
            double dailyRate = scanner.nextDouble();
            scanner.nextLine(); // Clear buffer

            // Rent the vehicle
            if (rentVehicle(connection, vehicleId, customerId, dailyRate)) {
                System.out.println("Car rented successfully!");
            } else {
                System.out.println("Failed to rent the car.");
            }
        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();
        }
    }

    private void listAvailableVehicles(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Vehicles WHERE Status = 'Available'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Vehicle ID: " + rs.getInt("VehicleID") + ", Make: " + rs.getString("Make") + ", Model: " + rs.getString("Model"));
            }
        }
    }

    private boolean isVehicleAvailable(Connection connection, int vehicleId) throws SQLException {
        String sql = "SELECT Status FROM Vehicles WHERE VehicleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String status = rs.getString("Status");
                return status.equals("Available");
            }
        }
        return false;
    }

    private boolean rentVehicle(Connection connection, int vehicleId, int customerId, double dailyRate) throws SQLException {
        String sql = "INSERT INTO Rentals (VehicleID, CustomerID, RentalDate, DailyRate) VALUES (?, ?, CURDATE(), ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            pstmt.setInt(2, customerId);
            pstmt.setDouble(3, dailyRate);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public static void main(String[] args) {
        RentalProcess rentalProcess = new RentalProcess();
        rentalProcess.startRentalProcess();
    }

    public void startRental() {
    }
}
