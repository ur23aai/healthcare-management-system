package util;
import java.io.*;
import java.util.List;

public class CSVWriter {
    public static void writeCSV(String filePath, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    String value = row[i];
                    if (value.contains(",") || value.contains("\"")) {
                        value = "\"" + value.replace("\"", "\"\"") + "\"";
                    }
                    line.append(value);
                    if (i < row.length - 1) line.append(",");
                }
                bw.write(line.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }
    
    public static void appendCSV(String filePath, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            for (String[] row : data) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    String value = row[i];
                    if (value.contains(",") || value.contains("\"")) {
                        value = "\"" + value.replace("\"", "\"\"") + "\"";
                    }
                    line.append(value);
                    if (i < row.length - 1) line.append(",");
                }
                bw.write(line.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error appending to CSV: " + e.getMessage());
        }
    }
}
