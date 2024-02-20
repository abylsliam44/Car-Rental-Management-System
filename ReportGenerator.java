import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {
    public static void generateReport(List<String> reportData, String filePath, ReportFormat format) {
        switch (format) {
            case JSON:
                generateJsonReport(reportData, filePath);
                break;
            case TXT:
                generateTxtReport(reportData, filePath);
                break;
            case CSV:
                generateCsvReport(reportData, filePath);
                break;
            default:
                System.out.println("Unsupported report format");
        }
    }

    private static void generateJsonReport(List<String> reportData, String filePath) {
        /* Gson gson = new Gson();
        String jsonData = gson.toJson(reportData);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        } */
    }

    private static void generateTxtReport(List<String> reportData, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String line : reportData) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateCsvReport(List<String> reportData, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String line : reportData) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum ReportFormat {
        JSON,
        TXT,
        CSV
    }
}
