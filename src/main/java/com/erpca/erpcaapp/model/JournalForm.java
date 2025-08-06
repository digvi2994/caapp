package com.erpca.erpcaapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class JournalForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String journalNo;
    private String referenceNo;
    private LocalDate date;
    private String costCenter;
    private String particulars;
    private String itemName;
    private long quantity;
    private double rate;
    private String drCr;
    private double amount;
    private String ledgerNarration;
    private String narration;
    private double amount1;
    private String drCr1;
    private String ledgerNarration1;
    private double amount2;
    private String drCr2;
    private String ledgerNarration2;
    private long userId;
    private long customerId;
}
