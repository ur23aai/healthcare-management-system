package util;
import java.io.*;
import java.util.*;

public class CSVReader {
    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "").trim();
                }
                data.add(values);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + filePath);
        }
        return data;
    }
}
