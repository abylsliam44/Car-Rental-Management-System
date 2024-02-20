import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfitCalculator {
    public static double calculateTotalProfit() {
        double totalProfit = 0.0;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Rentals");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int vehicleId = rs.getInt("VehicleID");
                double dailyRate = rs.getDouble("DailyRate");
                int rentalDays = calculateRentalDays(rs.getDate("RentalDate"), rs.getDate("ReturnDate"));

                totalProfit += calculateProfitForRental(vehicleId, dailyRate, rentalDays);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while calculating total profit.");
            e.printStackTrace();
        }

        return totalProfit;
    }

    private static int calculateRentalDays(java.sql.Date startDate, java.sql.Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }

        long millisPerDay = 24 * 60 * 60 * 1000;
        return (int) ((endDate.getTime() - startDate.getTime()) / millisPerDay);
    }

    private static double calculateProfitForRental(int vehicleId, double dailyRate, int rentalDays) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT DailyRentalPrice FROM Vehicles WHERE VehicleID = ?")) {
            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double dailyRentalPrice = rs.getDouble("DailyRentalPrice");
                return dailyRentalPrice * rentalDays;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while calculating profit for a rental.");
            e.printStackTrace();
        }

        return 0.0;
    }

    public static void main(String[] args) {
        double totalProfit = calculateTotalProfit();
        System.out.println("Total Profit: $" + totalProfit);
    }
}
