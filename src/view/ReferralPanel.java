package view;
import controller.*;
import model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ReferralPanel extends JPanel {
    private ReferralController controller;
    private PatientController patientController;
    private JTable table;
    private DefaultTableModel tableModel;

    public ReferralPanel(ReferralController controller, PatientController patientController) {
        this.controller = controller;
        this.patientController = patientController;
        setLayout(new BorderLayout());

        // Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("🏥 Referral Management (Singleton Pattern)");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Referral ID", "Patient ID", "Referring Dr", "Specialist", "Reason", "Urgency", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons - Full CRUD + Generate Email
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("➕ Add");
        JButton editButton = new JButton("✏️ Edit");
        JButton deleteButton = new JButton("🗑️ Delete");
        JButton emailButton = new JButton("📧 Generate Email");
        JButton refreshButton = new JButton("🔄 Refresh");

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> deleteReferral());
        emailButton.addActionListener(e -> generateEmail());
        refreshButton.addActionListener(e -> refreshTable());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(emailButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Referral r : controller.getAllReferrals()) {
            tableModel.addRow(new Object[]{
                    r.getReferralID(), r.getPatientID(), r.getReferringClinicianID(),
                    r.getReferredToClinicianID(), r.getReferralReason(), r.getUrgencyLevel(), r.getStatus()
            });
        }
    }

    private void showAddDialog() {
        JTextField patientIDField = new JTextField(15);
        JTextField reasonField = new JTextField(15);
        JTextArea summaryArea = new JTextArea(3, 15);
        JTextField investigationsField = new JTextField(15);
        JComboBox<String> urgencyCombo = new JComboBox<>(new String[]{"Routine", "Urgent", "Emergency"});

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Patient ID:")); panel.add(patientIDField);
        panel.add(new JLabel("Reason:")); panel.add(reasonField);
        panel.add(new JLabel("Summary:")); panel.add(new JScrollPane(summaryArea));
        panel.add(new JLabel("Investigations:")); panel.add(investigationsField);
        panel.add(new JLabel("Urgency:")); panel.add(urgencyCombo);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Referral", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Referral r = new Referral(controller.generateNextReferralID(), patientIDField.getText(),
                    "C001", "C005", "S001", "H001", LocalDate.now(), (String)urgencyCombo.getSelectedItem(),
                    reasonField.getText(), summaryArea.getText(), investigationsField.getText(),
                    "New", "", "", LocalDate.now(), LocalDate.now());
            controller.addReferral(r);
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Referral added!");
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a referral first");
            return;
        }

        String id = (String) tableModel.getValueAt(row, 0);
        Referral r = controller.getReferralById(id);

        JTextField statusField = new JTextField(r.getStatus(), 15);
        JTextField notesField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Status:")); panel.add(statusField);
        panel.add(new JLabel("Notes:")); panel.add(notesField);

        if (JOptionPane.showConfirmDialog(this, panel, "Edit Referral", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            controller.updateReferral(id, statusField.getText(), notesField.getText());
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Updated!");
        }
    }

    private void deleteReferral() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a referral first");
            return;
        }

        String id = (String) tableModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete referral " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.deleteReferral(id);
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Deleted!");
        }
    }

    private void generateEmail() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a referral first");
            return;
        }

        String id = (String) tableModel.getValueAt(row, 0);
        Referral r = controller.getReferralById(id);
        Patient p = patientController.getPatientById(r.getPatientID());
        String name = (p != null) ? p.getFirstName() + " " + p.getLastName() : "Unknown";

        ReferralManager manager = ReferralManager.getInstance();
        String email = manager.generateReferralEmail(r, name, "Dr. David Thompson", "Dr. Richard Evans", "Heartlands Hospital");
        manager.saveReferralToFile(email, id);
        manager.updateEHR(r);

        JOptionPane.showMessageDialog(this, "✅ Email saved to output/new_referrals.txt\n\nSingleton Pattern Used!");
    }
}