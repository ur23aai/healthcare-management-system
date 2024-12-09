package model;

public class MedicalRecord {
    private String recordID, diagnosis, treatmentHistory;

    public MedicalRecord(String recordID, String diagnosis, String treatmentHistory) {
        this.recordID = recordID;
        this.diagnosis = diagnosis;
        this.treatmentHistory = treatmentHistory;
    }

    public void viewRecord() {}
    public void updateRecord(String newDiagnosis, String newTreatment) {
        this.diagnosis = newDiagnosis;
        this.treatmentHistory = newTreatment;
    }

    public String getRecordID() { return recordID; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatmentHistory() { return treatmentHistory; }
}
