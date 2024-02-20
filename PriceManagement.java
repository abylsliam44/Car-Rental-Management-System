import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PriceManagement {

    private Scanner scanner;

    public PriceManagement() {
        this.scanner = new Scanner(System.in);
    }

    public void setRentalPrice() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            VehicleManagement vehicleManagement = new VehicleManagement();
            vehicleManagement.listAllVehicles();

            System.out.print("Enter Vehicle ID to set rental price: ");
            int vehicleId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Daily Rental Price (Tenge): ");
            double dailyPrice = scanner.nextDouble();
            scanner.nextLine();

            if (updateRentalPrice(connection, vehicleId, dailyPrice)) {
                System.out.println("Rental price set successfully!");
            } else {
                System.out.println("Failed to set rental price.");
            }
        } catch (SQLException e) {
            handleSQLException("Error occurred while setting rental price.", e);
        }
    }

    private boolean updateRentalPrice(Connection connection, int vehicleId, double dailyPrice) throws SQLException {
        String sql = "UPDATE Vehicles SET DailyRentalPrice = ? WHERE VehicleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, dailyPrice);
            pstmt.setInt(2, vehicleId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private void handleSQLException(String message, SQLException e) {
        System.out.println(message);
        e.printStackTrace();
    }

    public static void main(String[] args) {
        PriceManagement priceManagement = new PriceManagement();
        priceManagement.setRentalPrice();
    }
}
