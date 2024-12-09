package controller;

import model.Appointment;
import util.CSVReader;
import util.CSVWriter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentController {
    private List<Appointment> appointments;

    public AppointmentController() {
        this.appointments = new ArrayList<>();
    }

    // Load appointments from CSV
    public void loadAppointments(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        appointments.clear();

        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            try {
                Appointment appointment = new Appointment(
                        row[0], row[1], row[2], row[3], LocalDate.parse(row[4]), LocalTime.parse(row[5]),
                        Integer.parseInt(row[6]), row[7], row[8], row[9], row[10],
                        LocalDate.parse(row[11]), LocalDate.parse(row[12])
                );
                appointments.add(appointment);
            } catch (Exception e) {
                System.err.println("Error parsing appointment data: " + e.getMessage());
            }
        }
        System.out.println("Loaded " + appointments.size() + " appointments");
    }

    // Add new appointment and save
    public boolean addAppointment(Appointment appointment) {
        if (appointment != null) {
            appointments.add(appointment);
            appointment.book();
            saveAppointments("data/appointments.csv");
            return true;
        }
        return false;
    }

    // Update appointment and save
    public boolean updateAppointment(String appointmentID, LocalDate newDate, LocalTime newTime, String status) {
        Appointment appointment = getAppointmentById(appointmentID);
        if (appointment != null) {
            appointment.modify(newDate, newTime);
            appointment.setStatus(status);
            saveAppointments("data/appointments.csv");
            return true;
        }
        return false;
    }

    // Delete/Cancel appointment and save
    public boolean deleteAppointment(String appointmentID) {
        Appointment appointment = getAppointmentById(appointmentID);
        if (appointment != null) {
            appointment.cancel();
            boolean removed = appointments.removeIf(a -> a.getAppointmentID().equals(appointmentID));
            if (removed) {
                saveAppointments("data/appointments.csv");
            }
            return removed;
        }
        return false;
    }

    // Get appointment by ID
    public Appointment getAppointmentById(String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }
        return null;
    }

    // Get all appointments
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    // Save appointments to CSV
    public void saveAppointments(String filePath) {
        List<String[]> data = new ArrayList<>();

        data.add(new String[]{"appointment_id", "patient_id", "clinician_id", "facility_id",
                "appointment_date", "appointment_time", "duration_minutes", "appointment_type",
                "status", "reason_for_visit", "notes", "created_date", "last_modified"});

        for (Appointment a : appointments) {
            data.add(new String[]{
                    a.getAppointmentID(), a.getPatientID(), a.getClinicianID(), a.getFacilityID(),
                    a.getAppointmentDate().toString(), a.getAppointmentTime().toString(),
                    String.valueOf(a.getDurationMinutes()), a.getAppointmentType(), a.getStatus(),
                    a.getReasonForVisit(), a.getNotes(), a.getCreatedDate().toString(),
                    a.getLastModified().toString()
            });
        }

        CSVWriter.writeCSV(filePath, data);
        System.out.println("Appointments saved to " + filePath);
    }

    // Generate next appointment ID
    public String generateNextAppointmentID() {
        int maxId = 0;
        for (Appointment appointment : appointments) {
            String id = appointment.getAppointmentID();
            if (id.startsWith("A")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException e) {}
            }
        }
        return String.format("A%03d", maxId + 1);
    }
}