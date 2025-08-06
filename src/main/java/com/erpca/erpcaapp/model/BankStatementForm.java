package com.erpca.erpcaapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class BankStatementForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String chequeNo;
    private String description;
    private String ledger;
    private double debit;
    private double credit;
    private double balance;
    private long customerId;
    private long userId;
}
