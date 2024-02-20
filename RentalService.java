import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RentalService {
    private Scanner scanner;

    public RentalService() {
        this.scanner = new Scanner(System.in);
    }

    public void startRentalProcess() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            showAvailableCars(connection);

            System.out.print("Enter Vehicle ID to rent: ");
            int vehicleId = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            System.out.print("Enter Customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            System.out.print("Enter Daily Rate: ");
            double dailyRate = scanner.nextDouble();
            scanner.nextLine(); // очистка буфера

            if (rentCar(connection, vehicleId, customerId, dailyRate)) {
                System.out.println("Car rented successfully!");
            } else {
                System.out.println("Failed to rent the car.");
            }
        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();
        }
    }

    private void showAvailableCars(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Vehicles WHERE Status = 'Available'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Vehicle ID: " + rs.getInt("VehicleID") + ", Make: " + rs.getString("Make") + ", Model: " + rs.getString("Model"));
            }
        }
    }

    private boolean rentCar(Connection connection, int vehicleId, int customerId, double dailyRate) throws SQLException {
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
        RentalService rentalService = new RentalService();
        rentalService.startRentalProcess();
    }
}
