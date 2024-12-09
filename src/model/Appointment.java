package model;
import java.time.*;

public class Appointment {
    private String appointmentID, patientID, clinicianID, facilityID;
    private LocalDate appointmentDate, createdDate, lastModified;
    private LocalTime appointmentTime;
    private int durationMinutes;
    private String appointmentType, status, reasonForVisit, notes;

    public Appointment(String appointmentID, String patientID, String clinicianID, String facilityID,
                       LocalDate appointmentDate, LocalTime appointmentTime, int durationMinutes,
                       String appointmentType, String status, String reasonForVisit, String notes,
                       LocalDate createdDate, LocalDate lastModified) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.clinicianID = clinicianID;
        this.facilityID = facilityID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.durationMinutes = durationMinutes;
        this.appointmentType = appointmentType;
        this.status = status;
        this.reasonForVisit = reasonForVisit;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastModified = lastModified;
    }

    public void book() { this.status = "Scheduled"; }

    public void modify(LocalDate newDate, LocalTime newTime) {
        this.appointmentDate = newDate;
        this.appointmentTime = newTime;
        this.lastModified = LocalDate.now();
    }

    public void cancel() { this.status = "Cancelled"; }

    // Getters
    public String getAppointmentID() { return appointmentID; }
    public String getPatientID() { return patientID; }
    public String getClinicianID() { return clinicianID; }
    public String getFacilityID() { return facilityID; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public int getDurationMinutes() { return durationMinutes; }
    public String getAppointmentType() { return appointmentType; }
    public String getStatus() { return status; }
    public String getReasonForVisit() { return reasonForVisit; }
    public String getNotes() { return notes; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getLastModified() { return lastModified; }

    // Setters - ENSURE THESE EXIST
    public void setAppointmentID(String appointmentID) { this.appointmentID = appointmentID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }
    public void setClinicianID(String clinicianID) { this.clinicianID = clinicianID; }
    public void setFacilityID(String facilityID) { this.facilityID = facilityID; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }
    public void setStatus(String status) { this.status = status; }  // ⬅️ ENSURE THIS EXISTS
    public void setReasonForVisit(String reasonForVisit) { this.reasonForVisit = reasonForVisit; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
    public void setLastModified(LocalDate lastModified) { this.lastModified = lastModified; }

    @Override
    public String toString() {
        return appointmentID + " - " + appointmentDate + " " + appointmentTime;
    }
}