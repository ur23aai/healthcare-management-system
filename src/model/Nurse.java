package model;

public class Nurse {
    private String nurseID;
    private String name;
    private String department;

    public Nurse(String nurseID, String name, String department) {
        this.nurseID = nurseID;
        this.name = name;
        this.department = department;
    }

    public void recordObservation() {}
    public void updateMedicalRecord() {}
    public String getNurseID() { return nurseID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
}
