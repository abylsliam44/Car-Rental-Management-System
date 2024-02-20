import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class VehicleManagement {
    private Scanner scanner;

    public VehicleManagement() {
        this.scanner = new Scanner(System.in);
    }

    public void addNewVehicle() {
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

            String sql = "INSERT INTO Vehicles (LicensePlate, Make, Model, Year, Status) VALUES (?, ?, ?, ?, 'Available')";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, licensePlate);
                pstmt.setString(2, make);
                pstmt.setString(3, model);
                pstmt.setInt(4, year);

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

    public void updateVehicleStatus() {
        System.out.println("Updating vehicle status.");
        // Implement functionality to update the status of a vehicle
    }

    public void listAllVehicles() {
        System.out.println("List of all vehicles:");
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Vehicles");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Vehicle ID: " + rs.getInt("VehicleID") + ", License Plate: " + rs.getString("LicensePlate") +
                        ", Make: " + rs.getString("Make") + ", Model: " + rs.getString("Model") +
                        ", Year: " + rs.getInt("Year") + ", Status: " + rs.getString("Status"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while listing vehicles.");
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        VehicleManagement vehicleManagement = new VehicleManagement();
        vehicleManagement.addNewVehicle();
        vehicleManagement.listAllVehicles();
    }
}
