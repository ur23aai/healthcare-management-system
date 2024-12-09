package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String patientID;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nhsNumber;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;
    private String postcode;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private LocalDate registrationDate;
    private String gpSurgeryID;
    private List<MedicalRecord> medicalRecords;

    public Patient(String patientID, String firstName, String lastName, LocalDate dateOfBirth,
                   String nhsNumber, String gender, String phoneNumber, String email,
                   String address, String postcode, String emergencyContactName,
                   String emergencyContactPhone, LocalDate registrationDate, String gpSurgeryID) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nhsNumber = nhsNumber;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.registrationDate = registrationDate;
        this.gpSurgeryID = gpSurgeryID;
        this.medicalRecords = new ArrayList<>();
    }

    public void register() {
        System.out.println("Patient " + firstName + " " + lastName + " registered successfully.");
    }

    public void updateDetails(String address, String phoneNumber, String email) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        System.out.println("Patient details updated.");
    }

    public void viewAppointments() {
        System.out.println("Viewing appointments for patient: " + firstName + " " + lastName);
    }

    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getNhsNumber() { return nhsNumber; }
    public void setNhsNumber(String nhsNumber) { this.nhsNumber = nhsNumber; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    public String getGpSurgeryID() { return gpSurgeryID; }
    public void setGpSurgeryID(String gpSurgeryID) { this.gpSurgeryID = gpSurgeryID; }
    public List<MedicalRecord> getMedicalRecords() { return medicalRecords; }
    public void addMedicalRecord(MedicalRecord record) { this.medicalRecords.add(record); }

    @Override
    public String toString() {
        return patientID + " - " + firstName + " " + lastName + " (NHS: " + nhsNumber + ")";
    }
}
