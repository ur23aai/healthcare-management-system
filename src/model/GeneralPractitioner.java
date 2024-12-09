package model;

public class GeneralPractitioner extends Doctor {
    private int gpID;
    
    public GeneralPractitioner(String doctorID, String name, String specialization, int gpID) {
        super(doctorID, name, specialization);
        this.gpID = gpID;
    }

    @Override
    public void createPrescription() {
        System.out.println("GP " + name + " is creating a prescription.");
    }

    @Override
    public void sendReferral() {
        System.out.println("GP " + name + " is sending a referral to specialist.");
    }

    @Override
    public void updateMedicalRecord() {
        System.out.println("GP " + name + " is updating medical record.");
    }

    public void referToSpecialist() {
        System.out.println("GP " + name + " is referring patient to specialist.");
    }

    public int getGpID() { return gpID; }
    public void setGpID(int gpID) { this.gpID = gpID; }
}
