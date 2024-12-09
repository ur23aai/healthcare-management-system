package model;
import java.time.LocalDate;

public class Staff {
    private String staffID, firstName, lastName, role, department, facilityID;
    private String phoneNumber, email, employmentStatus, lineManager, accessLevel;
    private LocalDate startDate;

    public Staff(String staffID, String firstName, String lastName, String role, String department,
                String facilityID, String phoneNumber, String email, String employmentStatus,
                LocalDate startDate, String lineManager, String accessLevel) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.facilityID = facilityID;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
        this.lineManager = lineManager;
        this.accessLevel = accessLevel;
    }

    public String getStaffID() { return staffID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    
    @Override
    public String toString() {
        return staffID + " - " + firstName + " " + lastName;
    }
}
