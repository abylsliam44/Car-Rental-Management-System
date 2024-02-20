import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Customer {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Scanner scanner;

    public Customer() {
        this.scanner = new Scanner(System.in);
    }

    public void register() {
        System.out.println("Customer Registration:");
        System.out.print("Enter Full Name: ");
        this.fullName = scanner.nextLine();
        System.out.print("Enter Email: ");
        this.email = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        this.phoneNumber = scanner.nextLine();
        System.out.print("Enter Address: ");
        this.address = scanner.nextLine();
        System.out.println("Registration successful!");
    }

    public void rentCar() {
        System.out.println("Car Rental Process:");
        System.out.println("Choose a car from the available options:");
        System.out.println("Enter the ID of the car you want to rent:");
        int carId = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера
        System.out.println("Enter the start date and time of the rental (YYYY-MM-DD HH:MM):");
        String startDateTime = scanner.nextLine();
        String[] startParts = startDateTime.split(" ");
        String startDate = startParts[0];
        String startTime = startParts[1];
        System.out.println("Enter the end date and time of the rental (YYYY-MM-DD HH:MM):");
        String endDateTime = scanner.nextLine();
        String[] endParts = endDateTime.split(" ");
        String endDate = endParts[0];
        String endTime = endParts[1];

        // Внесение данных об аренде в таблицу Rentalss
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Rentalss (VehicleID, CustomerID, RentalTime, ReturnTime) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, carId);
                // Предполагается, что ID клиента будет 1 (замените на реальное значение, если есть)
                pstmt.setInt(2, 1);
                pstmt.setString(3, startDate + " " + startTime);
                pstmt.setString(4, endDate + " " + endTime);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Your rental has been confirmed!");
                } else {
                    System.out.println("Failed to confirm the rental.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while processing the rental.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.register(); // Регистрация клиента
        customer.rentCar(); // Процесс аренды автомобиля
    }
}
