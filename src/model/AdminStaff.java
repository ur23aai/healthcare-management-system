package model;
import java.util.*;

public class AdminStaff {
    private String staffID;
    private String name;
    private String role;
    private List<Appointment> managedAppointments;

    public AdminStaff(String staffID, String name, String role) {
        this.staffID = staffID;
        this.name = name;
        this.role = role;
        this.managedAppointments = new ArrayList<>();
    }

    public void registerPatient() {}
    public void manageAppointments(Appointment appointment) { managedAppointments.add(appointment); }
    public String getStaffID() { return staffID; }
    public String getName() { return name; }
}
