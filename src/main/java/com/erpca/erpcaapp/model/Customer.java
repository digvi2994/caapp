package com.erpca.erpcaapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustID")
    private Long custID;

    @Column(name = "CustName", length = 500, nullable = false)
    private String custName;

    @Column(name = "Address", length = 500)
    private String address;

    @Column(name = "Pincode", length = 10)
    private String pincode;

    @Column(name = "DigiPin", length = 10)
    private String digiPin;

    // --- getters & setters ---
    public Long getCustID() { return custID; }
    public void setCustID(Long custID) { this.custID = custID; }

    public String getCustName() { return custName; }
    public void setCustName(String custName) { this.custName = custName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getDigiPin() { return digiPin; }
    public void setDigiPin(String digiPin) { this.digiPin = digiPin; }
}
