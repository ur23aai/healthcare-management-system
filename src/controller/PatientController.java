package controller;

import model.Patient;
import util.CSVReader;
import util.CSVWriter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    private List<Patient> patients;

    public PatientController() {
        this.patients = new ArrayList<>();
    }

    // Load patients from CSV
    public void loadPatients(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        patients.clear();

        // Skip header row
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            try {
                Patient patient = new Patient(
                        row[0],  // patient_id
                        row[1],  // first_name
                        row[2],  // last_name
                        LocalDate.parse(row[3]),  // date_of_birth
                        row[4],  // nhs_number
                        row[5],  // gender
                        row[6],  // phone_number
                        row[7],  // email
                        row[8],  // address
                        row[9],  // postcode
                        row[10], // emergency_contact_name
                        row[11], // emergency_contact_phone
                        LocalDate.parse(row[12]), // registration_date
                        row[13]  // gp_surgery_id
                );
                patients.add(patient);
            } catch (Exception e) {
                System.err.println("Error parsing patient data: " + e.getMessage());
            }
        }
        System.out.println("Loaded " + patients.size() + " patients");
    }

    // Add new patient and save to CSV
    public boolean addPatient(Patient patient) {
        if (patient != null) {
            patients.add(patient);
            patient.register();
            // Auto-save after adding
            savePatients("data/patients.csv");
            return true;
        }
        return false;
    }

    // Update patient and save
    public boolean updatePatient(String patientID, String address, String phone, String email) {
        Patient patient = getPatientById(patientID);
        if (patient != null) {
            patient.updateDetails(address, phone, email);
            // Auto-save after updating
            savePatients("data/patients.csv");
            return true;
        }
        return false;
    }

    // Delete patient and save
    public boolean deletePatient(String patientID) {
        boolean removed = patients.removeIf(p -> p.getPatientID().equals(patientID));
        if (removed) {
            // Auto-save after deleting
            savePatients("data/patients.csv");
        }
        return removed;
    }

    // Get patient by ID
    public Patient getPatientById(String patientID) {
        for (Patient patient : patients) {
            if (patient.getPatientID().equals(patientID)) {
                return patient;
            }
        }
        return null;
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    // Search patients by name
    public List<Patient> searchPatientsByName(String name) {
        List<Patient> results = new ArrayList<>();
        String searchTerm = name.toLowerCase();
        for (Patient patient : patients) {
            if (patient.getFirstName().toLowerCase().contains(searchTerm) ||
                    patient.getLastName().toLowerCase().contains(searchTerm)) {
                results.add(patient);
            }
        }
        return results;
    }

    // Save patients to CSV
    public void savePatients(String filePath) {
        List<String[]> data = new ArrayList<>();

        // Add header
        data.add(new String[]{"patient_id", "first_name", "last_name", "date_of_birth", "nhs_number",
                "gender", "phone_number", "email", "address", "postcode",
                "emergency_contact_name", "emergency_contact_phone", "registration_date", "gp_surgery_id"});

        // Add patient data
        for (Patient p : patients) {
            data.add(new String[]{
                    p.getPatientID(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getDateOfBirth().toString(),
                    p.getNhsNumber(),
                    p.getGender(),
                    p.getPhoneNumber(),
                    p.getEmail(),
                    p.getAddress(),
                    p.getPostcode(),
                    p.getEmergencyContactName(),
                    p.getEmergencyContactPhone(),
                    p.getRegistrationDate().toString(),
                    p.getGpSurgeryID()
            });
        }

        CSVWriter.writeCSV(filePath, data);
        System.out.println("Patients saved to " + filePath);
    }

    // Generate next patient ID
    public String generateNextPatientID() {
        int maxId = 0;
        for (Patient patient : patients) {
            String id = patient.getPatientID();
            if (id.startsWith("P")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
        }
        return String.format("P%03d", maxId + 1);
    }
}