package com.erpca.erpcaapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class PurchaseForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String supplierInvNo;
    private LocalDate invoiceDate;
    private String gstNo;
    private String partyAccountName;
    private String placeOfSupply;
    private String purchaseLedger;
    private String itemName;
    private double quantity;
    private double rate;
    private double amount;
    private double sgst;
    private double cgst;
    private double igst;
    private double total;
    private double finalAmount;
    private long userId;
    private long customerId;

}
