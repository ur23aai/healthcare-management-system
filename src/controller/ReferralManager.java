package controller;
import model.Referral;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReferralManager {
    private static ReferralManager instance;
    private Queue<Referral> referralQueue;
    private List<String> auditTrail;
    
    private ReferralManager() {
        this.referralQueue = new LinkedList<>();
        this.auditTrail = new ArrayList<>();
        logAudit("ReferralManager initialized");
    }
    
    public static synchronized ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }
    
    public void addReferralToQueue(Referral referral) {
        referralQueue.offer(referral);
        logAudit("Referral added to queue: " + referral.getReferralID());
    }
    
    public Referral processNextReferral() {
        Referral referral = referralQueue.poll();
        if (referral != null) {
            logAudit("Processing referral: " + referral.getReferralID());
        }
        return referral;
    }
    
    public String generateReferralEmail(Referral referral, String patientName, String referringDoctorName, 
                                       String specialistName, String facilityName) {
        StringBuilder email = new StringBuilder();
        email.append("================== REFERRAL EMAIL ==================\n");
        email.append("Date: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n\n");
        email.append("To: ").append(specialistName).append("\n");
        email.append("From: ").append(referringDoctorName).append("\n");
        email.append("Facility: ").append(facilityName).append("\n\n");
        email.append("Subject: Patient Referral - ").append(patientName).append("\n");
        email.append("----------------------------------------------------\n\n");
        email.append("Referral ID: ").append(referral.getReferralID()).append("\n");
        email.append("Patient ID: ").append(referral.getPatientID()).append("\n");
        email.append("Patient Name: ").append(patientName).append("\n\n");
        email.append("Urgency Level: ").append(referral.getUrgencyLevel()).append("\n");
        email.append("Referral Reason: ").append(referral.getReferralReason()).append("\n\n");
        email.append("Clinical Summary:\n").append(referral.getClinicalSummary()).append("\n\n");
        email.append("Requested Investigations:\n").append(referral.getRequestedInvestigations()).append("\n\n");
        email.append("Notes: ").append(referral.getNotes()).append("\n");
        email.append("====================================================\n");
        
        logAudit("Email generated for referral: " + referral.getReferralID());
        return email.toString();
    }
    
    public boolean saveReferralToFile(String emailContent, String referralID) {
        try {
            FileWriter writer = new FileWriter("output/new_referrals.txt", true);
            writer.write(emailContent);
            writer.write("\n\n");
            writer.close();
            logAudit("Referral " + referralID + " saved to file");
            return true;
        } catch (IOException e) {
            logAudit("ERROR: Failed to save referral " + referralID);
            return false;
        }
    }
    
    public void updateEHR(Referral referral) {
        logAudit("EHR updated for patient: " + referral.getPatientID());
    }
    
    private void logAudit(String message) {
        String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        auditTrail.add("[" + timestamp + "] " + message);
    }
    
    public List<String> getAuditTrail() { return new ArrayList<>(auditTrail); }
    public int getQueueSize() { return referralQueue.size(); }
}
