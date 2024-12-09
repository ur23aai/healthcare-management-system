package view;
import controller.PatientController;
import model.Patient;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PatientPanel extends JPanel {
    private PatientController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public PatientPanel(PatientController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("👤 Patient Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Patient ID", "First Name", "Last Name", "NHS Number", "DOB", "Gender", "Phone", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel - FULL CRUD (Add, Edit, Delete, Refresh)
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("➕ Add");
        JButton editButton = new JButton("✏️ Edit");
        JButton deleteButton = new JButton("🗑️ Delete");
        JButton refreshButton = new JButton("🔄 Refresh");

        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        editButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));

        addButton.addActionListener(e -> showAddPatientDialog());
        editButton.addActionListener(e -> showEditPatientDialog());
        deleteButton.addActionListener(e -> deleteSelectedPatient());
        refreshButton.addActionListener(e -> refreshTable());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initial load
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Patient> patients = controller.getAllPatients();

        for (Patient p : patients) {
            tableModel.addRow(new Object[]{
                    p.getPatientID(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getNhsNumber(),
                    p.getDateOfBirth(),
                    p.getGender(),
                    p.getPhoneNumber(),
                    p.getEmail()
            });
        }

        System.out.println("Displayed " + patients.size() + " patients");
    }

    private void showAddPatientDialog() {
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField dobField = new JTextField("1990-01-01", 20);
        JTextField nhsField = new JTextField(20);
        JTextField genderField = new JTextField("M", 20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField postcodeField = new JTextField(20);
        JTextField emergencyContactField = new JTextField(20);
        JTextField emergencyPhoneField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(11, 2, 5, 5));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        panel.add(dobField);
        panel.add(new JLabel("NHS Number:"));
        panel.add(nhsField);
        panel.add(new JLabel("Gender (M/F):"));
        panel.add(genderField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Postcode:"));
        panel.add(postcodeField);
        panel.add(new JLabel("Emergency Contact:"));
        panel.add(emergencyContactField);
        panel.add(new JLabel("Emergency Phone:"));
        panel.add(emergencyPhoneField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Patient", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String patientID = controller.generateNextPatientID();

                Patient patient = new Patient(
                        patientID,
                        firstNameField.getText(),
                        lastNameField.getText(),
                        LocalDate.parse(dobField.getText()),
                        nhsField.getText(),
                        genderField.getText(),
                        phoneField.getText(),
                        emailField.getText(),
                        addressField.getText(),
                        postcodeField.getText(),
                        emergencyContactField.getText(),
                        emergencyPhoneField.getText(),
                        LocalDate.now(),
                        "S001"
                );

                controller.addPatient(patient);
                refreshTable();

                JOptionPane.showMessageDialog(this,
                        "✅ Patient added successfully!\n\n" +
                                "Patient ID: " + patientID + "\n" +
                                "Saved to: data/patients.csv",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage() + "\n\n" +
                                "Please check date format: YYYY-MM-DD",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditPatientDialog() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Please select a patient from the table first.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get selected patient ID
        String patientID = (String) tableModel.getValueAt(selectedRow, 0);
        Patient patient = controller.getPatientById(patientID);

        if (patient == null) {
            JOptionPane.showMessageDialog(this, "❌ Patient not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create edit dialog with current values
        JTextField phoneField = new JTextField(patient.getPhoneNumber(), 20);
        JTextField emailField = new JTextField(patient.getEmail(), 20);
        JTextField addressField = new JTextField(patient.getAddress(), 20);
        JTextField postcodeField = new JTextField(patient.getPostcode(), 20);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Patient ID:"));
        panel.add(new JLabel(patientID + " (cannot change)"));
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Postcode:"));
        panel.add(postcodeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Patient Details", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                controller.updatePatient(
                        patientID,
                        addressField.getText(),
                        phoneField.getText(),
                        emailField.getText()
                );

                refreshTable();

                JOptionPane.showMessageDialog(this,
                        "✅ Patient updated successfully!\n\n" +
                                "Patient ID: " + patientID + "\n" +
                                "Changes saved to: data/patients.csv",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteSelectedPatient() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Please select a patient from the table first.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String patientID = (String) tableModel.getValueAt(selectedRow, 0);
        String patientName = tableModel.getValueAt(selectedRow, 1) + " " + tableModel.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this patient?\n\n" +
                        "ID: " + patientID + "\n" +
                        "Name: " + patientName + "\n\n" +
                        "This action cannot be undone!",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.deletePatient(patientID);
            refreshTable();
            JOptionPane.showMessageDialog(this,
                    "✅ Patient deleted successfully!",
                    "Deleted", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}