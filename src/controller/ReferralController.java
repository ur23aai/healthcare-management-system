package controller;

import model.Referral;
import util.CSVReader;
import util.CSVWriter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReferralController {
    private List<Referral> referrals;

    public ReferralController() {
        this.referrals = new ArrayList<>();
    }

    // Load referrals from CSV
    public void loadReferrals(String filePath) {
        List<String[]> data = CSVReader.readCSV(filePath);
        referrals.clear();

        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            try {
                Referral r = new Referral(
                        row[0], row[1], row[2], row[3], row[4], row[5],
                        LocalDate.parse(row[6]), row[7], row[8], row[9], row[10], row[11],
                        row[12], row[13], LocalDate.parse(row[14]), LocalDate.parse(row[15])
                );
                referrals.add(r);
            } catch (Exception e) {
                System.err.println("Error parsing referral data: " + e.getMessage());
            }
        }
        System.out.println("Loaded " + referrals.size() + " referrals");
    }

    // Add new referral and SAVE TO CSV
    public boolean addReferral(Referral referral) {
        if (referral != null) {
            referrals.add(referral);
            referral.sendReferral();
            ReferralManager.getInstance().addReferralToQueue(referral);
            saveReferrals("data/referrals.csv");
            return true;
        }
        return false;
    }

    // Update referral and save
    public boolean updateReferral(String referralID, String status, String notes) {
        Referral referral = getReferralById(referralID);
        if (referral != null) {
            referral.setStatus(status);
            referral.setNotes(notes);
            referral.setLastUpdated(LocalDate.now());
            saveReferrals("data/referrals.csv");
            return true;
        }
        return false;
    }

    // Delete referral and save
    public boolean deleteReferral(String referralID) {
        boolean removed = referrals.removeIf(r -> r.getReferralID().equals(referralID));
        if (removed) {
            saveReferrals("data/referrals.csv");
        }
        return removed;
    }

    // Get referral by ID
    public Referral getReferralById(String referralID) {
        for (Referral referral : referrals) {
            if (referral.getReferralID().equals(referralID)) {
                return referral;
            }
        }
        return null;
    }

    // Get all referrals
    public List<Referral> getAllReferrals() {
        return new ArrayList<>(referrals);
    }

    // Save referrals to CSV
    public void saveReferrals(String filePath) {
        List<String[]> data = new ArrayList<>();

        data.add(new String[]{"referral_id", "patient_id", "referring_clinician_id", "referred_to_clinician_id",
                "referring_facility_id", "referred_to_facility_id", "referral_date", "urgency_level",
                "referral_reason", "clinical_summary", "requested_investigations", "status",
                "appointment_id", "notes", "created_date", "last_updated"});

        for (Referral r : referrals) {
            data.add(new String[]{
                    r.getReferralID(), r.getPatientID(), r.getReferringClinicianID(), r.getReferredToClinicianID(),
                    r.getReferringFacilityID(), r.getReferredToFacilityID(), r.getReferralDate().toString(),
                    r.getUrgencyLevel(), r.getReferralReason(), r.getClinicalSummary(),
                    r.getRequestedInvestigations(), r.getStatus(), r.getAppointmentID(), r.getNotes(),
                    r.getCreatedDate().toString(), r.getLastUpdated().toString()
            });
        }

        CSVWriter.writeCSV(filePath, data);
        System.out.println("Referrals saved to " + filePath);
    }

    // Generate next referral ID
    public String generateNextReferralID() {
        int maxId = 0;
        for (Referral r : referrals) {
            if (r.getReferralID().startsWith("R")) {
                try {
                    int num = Integer.parseInt(r.getReferralID().substring(1));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException e) {}
            }
        }
        return String.format("R%03d", maxId + 1);
    }
}