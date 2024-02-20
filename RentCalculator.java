import java.time.Duration;
import java.time.LocalDateTime;

public class RentCalculator {
    private double hourlyRate;

    public RentCalculator(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double calculateRentCost(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();
        return hours * hourlyRate;
    }
}
