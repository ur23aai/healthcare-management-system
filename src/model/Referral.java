package model;
import java.time.LocalDate;

public class Referral {
    private String referralID, patientID, referringClinicianID, referredToClinicianID;
    private String referringFacilityID, referredToFacilityID;
    private LocalDate referralDate, createdDate, lastUpdated;
    private String urgencyLevel, referralReason, clinicalSummary, requestedInvestigations;
    private String status, appointmentID, notes;

    public Referral(String referralID, String patientID, String referringClinicianID, String referredToClinicianID,
                    String referringFacilityID, String referredToFacilityID, LocalDate referralDate,
                    String urgencyLevel, String referralReason, String clinicalSummary,
                    String requestedInvestigations, String status, String appointmentID,
                    String notes, LocalDate createdDate, LocalDate lastUpdated) {
        this.referralID = referralID;
        this.patientID = patientID;
        this.referringClinicianID = referringClinicianID;
        this.referredToClinicianID = referredToClinicianID;
        this.referringFacilityID = referringFacilityID;
        this.referredToFacilityID = referredToFacilityID;
        this.referralDate = referralDate;
        this.urgencyLevel = urgencyLevel;
        this.referralReason = referralReason;
        this.clinicalSummary = clinicalSummary;
        this.requestedInvestigations = requestedInvestigations;
        this.status = status;
        this.appointmentID = appointmentID;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }

    public void sendReferral() {
        this.status = "Pending";
        this.createdDate = LocalDate.now();
    }

    public void receiveReferral() {
        this.status = "In Progress";
    }

    // Getters
    public String getReferralID() { return referralID; }
    public String getPatientID() { return patientID; }
    public String getReferringClinicianID() { return referringClinicianID; }
    public String getReferredToClinicianID() { return referredToClinicianID; }
    public String getReferringFacilityID() { return referringFacilityID; }
    public String getReferredToFacilityID() { return referredToFacilityID; }
    public LocalDate getReferralDate() { return referralDate; }
    public String getUrgencyLevel() { return urgencyLevel; }
    public String getReferralReason() { return referralReason; }
    public String getClinicalSummary() { return clinicalSummary; }
    public String getRequestedInvestigations() { return requestedInvestigations; }
    public String getStatus() { return status; }
    public String getAppointmentID() { return appointmentID; }
    public String getNotes() { return notes; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getLastUpdated() { return lastUpdated; }

    // Setters - ADD THESE IF MISSING
    public void setStatus(String status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }  // ⬅️ ADD THIS
    public void setLastUpdated(LocalDate date) { this.lastUpdated = date; }

    @Override
    public String toString() {
        return referralID + " - " + referralReason;
    }
}