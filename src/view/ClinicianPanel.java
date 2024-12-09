package view;
import util.CSVReader;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ClinicianPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    
    public ClinicianPanel() {
        setLayout(new BorderLayout());
        
        String[] columns = {"Clinician ID", "Name", "Title", "Specialty", "Workplace ID", "Workplace Type"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadClinicians());
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        loadClinicians();
    }
    
    private void loadClinicians() {
        tableModel.setRowCount(0);
        List<String[]> data = CSVReader.readCSV("data/clinicians.csv");
        
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            if (row.length >= 10) {
                tableModel.addRow(new Object[]{
                    row[0],  // clinician_id
                    row[2] + " " + row[1],  // title + first_name + last_name
                    row[3],  // title
                    row[4],  // specialty
                    row[8],  // workplace_id
                    row[9]   // workplace_type
                });
            }
        }
    }
}
