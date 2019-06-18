package com.example.bankbloodproject.model;

public class DonnerDetails {
    private String bloodgroup;
    private String longtude;
    private String latitude;

    public DonnerDetails() {
    }

    public DonnerDetails(String bloodgroup, String longtude,String latitude ) {
        this.bloodgroup = bloodgroup;
        this.longtude=longtude;
        this.latitude=latitude;

    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getLongtude() {
        return longtude;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
