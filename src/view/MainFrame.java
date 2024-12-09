package view;
import controller.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private PatientController patientController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;
    
    private JTabbedPane tabbedPane;
    
    public MainFrame() {
        // Initialize controllers
        patientController = new PatientController();
        appointmentController = new AppointmentController();
        prescriptionController = new PrescriptionController();
        referralController = new ReferralController();
        
        // Load data
        loadAllData();
        
        // Setup frame
        setTitle("Healthcare Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add panels
        tabbedPane.addTab("Patients", new PatientPanel(patientController));
        tabbedPane.addTab("Appointments", new AppointmentPanel(appointmentController, patientController));
        tabbedPane.addTab("Prescriptions", new PrescriptionPanel(prescriptionController, patientController));
        tabbedPane.addTab("Referrals", new ReferralPanel(referralController, patientController));
        
        add(tabbedPane);
    }
    
    private void loadAllData() {
        try {
            patientController.loadPatients("data/patients.csv");
            appointmentController.loadAppointments("data/appointments.csv");
            prescriptionController.loadPrescriptions("data/prescriptions.csv");
            referralController.loadReferrals("data/referrals.csv");
            JOptionPane.showMessageDialog(this, "Data loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
