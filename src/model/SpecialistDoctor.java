package model;

public class SpecialistDoctor extends Doctor {
    private String department;

    public SpecialistDoctor(String doctorID, String name, String specialization, String department) {
        super(doctorID, name, specialization);
        this.department = department;
    }

    @Override
    public void createPrescription() {}

    @Override
    public void sendReferral() {}

    @Override
    public void updateMedicalRecord() {}

    public void receiveReferral() {}
    public String getDepartment() { return department; }
}
