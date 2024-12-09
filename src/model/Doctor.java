package model;

public abstract class Doctor {
    protected String doctorID;
    protected String name;
    protected String specialization;

    public Doctor(String doctorID, String name, String specialization) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialization = specialization;
    }

    public abstract void createPrescription();
    public abstract void sendReferral();
    public abstract void updateMedicalRecord();

    public String getDoctorID() { return doctorID; }
    public void setDoctorID(String doctorID) { this.doctorID = doctorID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    @Override
    public String toString() {
        return doctorID + " - " + name + " (" + specialization + ")";
    }
}
