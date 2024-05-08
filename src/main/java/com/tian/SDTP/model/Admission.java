package com.tian.SDTP.model;

public class Admission {

    private int id;
    private String admissionDate;
    private String dischargeDate;
    private int patientID;

    // Constructor
    public Admission(int id, String admissionDate, String dischargeDate, int patientID) {
        this.id = id;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.patientID = patientID;
    }

    // Getters and setters (optional)
    // You may need getters and setters if you plan to access these fields externally
    // However, for simplicity, you can omit them if you only need to access these fields within this class
}
