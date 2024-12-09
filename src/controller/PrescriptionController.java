package controller;

import model.Prescription;
import util.CSVReader;
import util.CSVWriter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionController {
    private List<Prescription> prescriptions;

    public PrescriptionController() {
        this.prescriptions = new ArrayList<>();
    }

    // Load prescriptions from CSV - FIXED PARSING
    public void loadPrescriptions(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        prescriptions.clear();

        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            try {
                LocalDate collectionDate = (row.length > 14 && !row[14].isEmpty()) ? LocalDate.parse(row[14]) : null;

                // Extract numeric value from quantity field (handle "28 tablets" format)
                int quantity = extractNumericValue(row[9]);

                Prescription p = new Prescription(
                        row[0], row[1], row[2], row[3], LocalDate.parse(row[4]), row[5], row[6], row[7],
                        Integer.parseInt(row[8]), quantity, row[10], row[11], row[12], LocalDate.parse(row[13]), collectionDate
                );
                prescriptions.add(p);
            } catch (Exception e) {
                System.err.println("Error parsing prescription row " + i + ": " + e.getMessage());
            }
        }
        System.out.println("Loaded " + prescriptions.size() + " prescriptions");
    }

    // Helper method to extract numbers from "28 tablets" format
    private int extractNumericValue(String value) {
        try {
            String numericOnly = value.replaceAll("[^0-9]", "");
            if (numericOnly.isEmpty()) return 0;
            return Integer.parseInt(numericOnly);
        } catch (Exception e) {
            return 0;
        }
    }

    // Add new prescription and SAVE TO CSV
    public boolean addPrescription(Prescription prescription) {
        if (prescription != null) {
            prescriptions.add(prescription);
            prescription.issuePrescription();
            savePrescriptions("data/prescriptions.csv");
            appendPrescriptionToFile(prescription, "output/new_prescriptions.csv");
            return true;
        }
        return false;
    }

    // Update prescription and save
    public boolean updatePrescription(String prescriptionID, String status, String notes) {
        Prescription prescription = getPrescriptionById(prescriptionID);
        if (prescription != null) {
            prescription.setStatus(status);
            prescription.setNotes(notes);
            savePrescriptions("data/prescriptions.csv");
            return true;
        }
        return false;
    }

    // Delete prescription and save
    public boolean deletePrescription(String prescriptionID) {
        boolean removed = prescriptions.removeIf(p -> p.getPrescriptionID().equals(prescriptionID));
        if (removed) {
            savePrescriptions("data/prescriptions.csv");
        }
        return removed;
    }

    // Get prescription by ID
    public Prescription getPrescriptionById(String prescriptionID) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getPrescriptionID().equals(prescriptionID)) {
                return prescription;
            }
        }
        return null;
    }

    // Get all prescriptions
    public List<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions);
    }

    // Save prescriptions to CSV
    public void savePrescriptions(String filePath) {
        List<String[]> data = new ArrayList<>();

        data.add(new String[]{"prescription_id", "patient_id", "clinician_id", "appointment_id",
                "prescription_date", "medication_name", "dosage", "frequency",
                "duration_days", "quantity", "instructions", "pharmacy_name",
                "status", "issue_date", "collection_date"});

        for (Prescription p : prescriptions) {
            String collectionDate = (p.getCollectionDate() != null) ? p.getCollectionDate().toString() : "";
            data.add(new String[]{
                    p.getPrescriptionID(), p.getPatientID(), p.getClinicianID(), p.getAppointmentID(),
                    p.getPrescriptionDate().toString(), p.getMedicationName(), p.getDosage(), p.getFrequency(),
                    String.valueOf(p.getDurationDays()), String.valueOf(p.getQuantity()), p.getInstructions(),
                    p.getPharmacyName(), p.getStatus(), p.getIssueDate().toString(), collectionDate
            });
        }

        CSVWriter.writeCSV(filePath, data);
        System.out.println("Prescriptions saved to " + filePath);
    }

    // Append to output file
    public void appendPrescriptionToFile(Prescription p, String filePath) {
        List<String[]> newData = new ArrayList<>();
        String collectionDate = (p.getCollectionDate() != null) ? p.getCollectionDate().toString() : "";

        newData.add(new String[]{
                p.getPrescriptionID(), p.getPatientID(), p.getClinicianID(), p.getAppointmentID(),
                p.getPrescriptionDate().toString(), p.getMedicationName(), p.getDosage(), p.getFrequency(),
                String.valueOf(p.getDurationDays()), String.valueOf(p.getQuantity()), p.getInstructions(),
                p.getPharmacyName(), p.getStatus(), p.getIssueDate().toString(), collectionDate
        });

        CSVWriter.appendCSV(filePath, newData);
    }

    // Generate next prescription ID
    public String generateNextPrescriptionID() {
        int maxId = 0;
        for (Prescription p : prescriptions) {
            if (p.getPrescriptionID().startsWith("RX")) {
                try {
                    int num = Integer.parseInt(p.getPrescriptionID().substring(2));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException e) {}
            }
        }
        return String.format("RX%03d", maxId + 1);
    }
}