package model;
import java.time.LocalDate;

public class Prescription {
    private String prescriptionID, patientID, clinicianID, appointmentID;
    private LocalDate prescriptionDate, issueDate, collectionDate;
    private String medicationName, dosage, frequency, instructions, pharmacyName, status, notes;
    private int durationDays, quantity;

    public Prescription(String prescriptionID, String patientID, String clinicianID, String appointmentID,
                       LocalDate prescriptionDate, String medicationName, String dosage, String frequency,
                       int durationDays, int quantity, String instructions, String pharmacyName,
                       String status, LocalDate issueDate, LocalDate collectionDate) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.clinicianID = clinicianID;
        this.appointmentID = appointmentID;
        this.prescriptionDate = prescriptionDate;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.quantity = quantity;
        this.instructions = instructions;
        this.pharmacyName = pharmacyName;
        this.status = status;
        this.issueDate = issueDate;
        this.collectionDate = collectionDate;
        this.notes = "";
    }

    public void issuePrescription() { this.status = "Issued"; this.issueDate = LocalDate.now(); }
    
    public String getPrescriptionID() { return prescriptionID; }
    public String getPatientID() { return patientID; }
    public String getClinicianID() { return clinicianID; }
    public String getAppointmentID() { return appointmentID; }
    public LocalDate getPrescriptionDate() { return prescriptionDate; }
    public String getMedicationName() { return medicationName; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public int getDurationDays() { return durationDays; }
    public int getQuantity() { return quantity; }
    public String getInstructions() { return instructions; }
    public String getPharmacyName() { return pharmacyName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getCollectionDate() { return collectionDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    @Override
    public String toString() {
        return prescriptionID + " - " + medicationName + " (" + dosage + ")";
    }
}
