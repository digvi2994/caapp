package com.erpca.erpcaapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;
@Data
@Entity
public class ItemForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String under;
    private String category;
    private String unit;
    private String gstApplicable;
    private String typeOfSupply;
    private String hsnSac;
    private String setAlterGSTDetails;
    private String taxability;
    private double integratedTax;
    private double cessTax;
    private Date applicableDate;
    private double quantity;
    private double rate;
    private long userId;
    private long CustomerId;


}
