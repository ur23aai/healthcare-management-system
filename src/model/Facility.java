package model;

public class Facility {
    private String facilityID, facilityName, facilityType, address, postcode;
    private String phoneNumber, email, openingHours, managerName, specialitiesOffered;
    private int capacity;

    public Facility(String facilityID, String facilityName, String facilityType, String address,
                   String postcode, String phoneNumber, String email, String openingHours,
                   String managerName, int capacity, String specialitiesOffered) {
        this.facilityID = facilityID;
        this.facilityName = facilityName;
        this.facilityType = facilityType;
        this.address = address;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.openingHours = openingHours;
        this.managerName = managerName;
        this.capacity = capacity;
        this.specialitiesOffered = specialitiesOffered;
    }

    public String getFacilityID() { return facilityID; }
    public String getFacilityName() { return facilityName; }
    public String getFacilityType() { return facilityType; }
    
    @Override
    public String toString() {
        return facilityID + " - " + facilityName;
    }
}
