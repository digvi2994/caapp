// src/main/java/com/erpca/erpcaapp/model/DocInfo.java
package com.erpca.erpcaapp.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "DocInfo", schema = "dbo")
public class DocInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocID", nullable = false, updatable = false)
    private Integer docID;

    @Column(name = "DocName", length = 255, nullable = false)
    private String docName;

    @Column(name = "UploadDt", nullable = false)
    private LocalDate uploadDt;

    @Column(name = "CompltionDt", nullable = false)
    private LocalDate compltionDt;

    /** 'Y' or 'N' flag indicating whether “All of flag” was set. */
    @Column(name = "Alloflag", length = 1, nullable = false)
    private String alloflag;

    /**
     * FK to login_users.userID.
     * We can either map this as a raw integer or, if you prefer JPA relationships,
     * you can map it to a LoginUser entity. Below is a simple integer mapping.
     */
    @Column(name = "UserID", nullable = false)
    private Integer userID;
    
    // ← new field
    @Column(name="CustID")
    private Integer custID;
    
    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false, insertable = false, updatable = false)
    private LoginUser user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CustID", insertable=false, updatable=false)
    private Customer customer;

    public DocInfo() { }

    public DocInfo(
            Integer docID,
            String docName,
            LocalDate uploadDt,
            LocalDate compltionDt,
            String alloflag,
            Integer userID
    ) {
        this.docID = docID;
        this.docName = docName;
        this.uploadDt = uploadDt;
        this.compltionDt = compltionDt;
        this.alloflag = alloflag;
        this.userID = userID;
    }
    
    // transient getter
    @Transient
    public String getFileNameOnly() {
      if (docName == null) return "";
      return docName.substring(docName.lastIndexOf('/') + 1);
    }

    // ------------- Getters and setters -------------
//
//    public Integer getDocID() {
//        return docID;
//    }
//    public void setDocID(Integer docID) {
//        this.docID = docID;
//    }
//
//    public String getDocName() {
//        return docName;
//    }
//    public void setDocName(String docName) {
//        this.docName = docName;
//    }
//
//    public LocalDate getUploadDt() {
//        return uploadDt;
//    }
//    public void setUploadDt(LocalDate uploadDt) {
//        this.uploadDt = uploadDt;
//    }
//
//    public LocalDate getCompltionDt() {
//        return compltionDt;
//    }
//    public void setCompltionDt(LocalDate compltionDt) {
//        this.compltionDt = compltionDt;
//    }
//
//    public String getAlloflag() {
//        return alloflag;
//    }
//    public void setAlloflag(String alloflag) {
//        this.alloflag = alloflag;
//    }
//
//    public Integer getUserID() {
//        return userID;
//    }
//    public void setUserID(Integer userID) {
//        this.userID = userID;
//    }
//
//    // getters & setters …
//    public Integer getCustID() {
//    	return custID;
//    }
//    public void setCustID(Integer custID) {
//    	this.custID = custID;
//    }
}