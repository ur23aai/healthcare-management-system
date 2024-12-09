package view;
import controller.*;
import model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.*;
import java.util.List;

public class AppointmentPanel extends JPanel {
    private AppointmentController controller;
    private PatientController patientController;
    private JTable table;
    private DefaultTableModel tableModel;

    public AppointmentPanel(AppointmentController controller, PatientController patientController) {
        this.controller = controller;
        this.patientController = patientController;
        setLayout(new BorderLayout());

        // Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("📅 Appointment Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Date", "Time", "Type", "Status", "Reason"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons - Full CRUD
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("➕ Add");
        JButton editButton = new JButton("✏️ Edit");
        JButton deleteButton = new JButton("🗑️ Delete");
        JButton refreshButton = new JButton("🔄 Refresh");

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> deleteAppointment());
        refreshButton.addActionListener(e -> refreshTable());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Appointment a : controller.getAllAppointments()) {
            tableModel.addRow(new Object[]{
                    a.getAppointmentID(), a.getPatientID(), a.getClinicianID(),
                    a.getAppointmentDate(), a.getAppointmentTime(), a.getAppointmentType(),
                    a.getStatus(), a.getReasonForVisit()
            });
        }
    }

    private void showAddDialog() {
        JTextField patientIDField = new JTextField(15);
        JTextField clinicianIDField = new JTextField("C001", 15);
        JTextField facilityIDField = new JTextField("S001", 15);
        JTextField dateField = new JTextField(LocalDate.now().toString(), 15);
        JTextField timeField = new JTextField("09:00", 15);
        JTextField reasonField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.add(new JLabel("Patient ID:")); panel.add(patientIDField);
        panel.add(new JLabel("Clinician ID:")); panel.add(clinicianIDField);
        panel.add(new JLabel("Facility ID:")); panel.add(facilityIDField);
        panel.add(new JLabel("Date (YYYY-MM-DD):")); panel.add(dateField);
        panel.add(new JLabel("Time (HH:MM):")); panel.add(timeField);
        panel.add(new JLabel("Reason:")); panel.add(reasonField);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Appointment", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Appointment a = new Appointment(controller.generateNextAppointmentID(),
                        patientIDField.getText(), clinicianIDField.getText(), facilityIDField.getText(),
                        LocalDate.parse(dateField.getText()), LocalTime.parse(timeField.getText()),
                        15, "Consultation", "Scheduled", reasonField.getText(), "",
                        LocalDate.now(), LocalDate.now());
                controller.addAppointment(a);
                refreshTable();
                JOptionPane.showMessageDialog(this, "✅ Appointment added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an appointment first");
            return;
        }

        String id = (String) tableModel.getValueAt(row, 0);
        Appointment a = controller.getAppointmentById(id);

        JTextField dateField = new JTextField(a.getAppointmentDate().toString(), 15);
        JTextField timeField = new JTextField(a.getAppointmentTime().toString(), 15);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Scheduled", "Completed", "Cancelled"});
        statusCombo.setSelectedItem(a.getStatus());

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Date:")); panel.add(dateField);
        panel.add(new JLabel("Time:")); panel.add(timeField);
        panel.add(new JLabel("Status:")); panel.add(statusCombo);

        if (JOptionPane.showConfirmDialog(this, panel, "Edit Appointment", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            controller.updateAppointment(id, LocalDate.parse(dateField.getText()),
                    LocalTime.parse(timeField.getText()), (String)statusCombo.getSelectedItem());
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Updated!");
        }
    }

    private void deleteAppointment() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an appointment first");
            return;
        }

        String id = (String) tableModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete appointment " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.deleteAppointment(id);
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Deleted!");
        }
    }
}