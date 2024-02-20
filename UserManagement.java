import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserManagement {
    private Scanner scanner;

    public UserManagement() {
        this.scanner = new Scanner(System.in);
    }

    public void addNewUser() {
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

    public void listAllUsers() {
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

    public static void main(String[] args) {
        UserManagement userManagement = new UserManagement();
        userManagement.addNewUser();
        userManagement.listAllUsers();
    }
}
