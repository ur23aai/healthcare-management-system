package view;
import controller.*;
import model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PrescriptionPanel extends JPanel {
    private PrescriptionController controller;
    private PatientController patientController;
    private JTable table;
    private DefaultTableModel tableModel;

    public PrescriptionPanel(PrescriptionController controller, PatientController patientController) {
        this.controller = controller;
        this.patientController = patientController;
        setLayout(new BorderLayout());

        // Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("💊 Prescription Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Prescription ID", "Patient ID", "Medication", "Dosage", "Frequency", "Duration", "Status", "Pharmacy"};
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
        deleteButton.addActionListener(e -> deletePrescription());
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
        for (Prescription p : controller.getAllPrescriptions()) {
            tableModel.addRow(new Object[]{
                    p.getPrescriptionID(), p.getPatientID(), p.getMedicationName(), p.getDosage(),
                    p.getFrequency(), p.getDurationDays() + " days", p.getStatus(), p.getPharmacyName()
            });
        }
    }

    private void showAddDialog() {
        JTextField patientIDField = new JTextField(15);
        JTextField medicationField = new JTextField(15);
        JTextField dosageField = new JTextField(15);
        JTextField frequencyField = new JTextField(15);
        JTextField durationField = new JTextField(15);
        JTextField quantityField = new JTextField(15);
        JTextField instructionsField = new JTextField(15);
        JTextField pharmacyField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.add(new JLabel("Patient ID:")); panel.add(patientIDField);
        panel.add(new JLabel("Medication:")); panel.add(medicationField);
        panel.add(new JLabel("Dosage:")); panel.add(dosageField);
        panel.add(new JLabel("Frequency:")); panel.add(frequencyField);
        panel.add(new JLabel("Duration (days):")); panel.add(durationField);
        panel.add(new JLabel("Quantity:")); panel.add(quantityField);
        panel.add(new JLabel("Instructions:")); panel.add(instructionsField);
        panel.add(new JLabel("Pharmacy:")); panel.add(pharmacyField);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Prescription", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Prescription p = new Prescription(controller.generateNextPrescriptionID(),
                        patientIDField.getText(), "C001", "", LocalDate.now(), medicationField.getText(),
                        dosageField.getText(), frequencyField.getText(), Integer.parseInt(durationField.getText()),
                        Integer.parseInt(quantityField.getText()), instructionsField.getText(),
                        pharmacyField.getText(), "Issued", LocalDate.now(), null);
                controller.addPrescription(p);
                refreshTable();
                JOptionPane.showMessageDialog(this, "✅ Prescription added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a prescription first");
            return;
        }

        String id = (String) tableModel.getValueAt(row, 0);
        Prescription p = controller.getPrescriptionById(id);

        JTextField statusField = new JTextField(p.getStatus(), 15);
        JTextField notesField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Status:")); panel.add(statusField);
        panel.add(new JLabel("Notes:")); panel.add(notesField);

        if (JOptionPane.showConfirmDialog(this, panel, "Edit Prescription", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            controller.updatePrescription(id, statusField.getText(), notesField.getText());
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Updated!");
        }
    }

    private void deletePrescription() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a prescription first");
            return;
        }

        String id = (String) tableModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete prescription " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.deletePrescription(id);
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Deleted!");
        }
    }
}