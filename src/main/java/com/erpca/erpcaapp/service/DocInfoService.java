// src/main/java/com/erpca/erpcaapp/service/DocInfoService.java
package com.erpca.erpcaapp.service;

import java.util.List;

import com.erpca.erpcaapp.model.DocInfo;

public interface DocInfoService {

    /** List all documents */
    List<DocInfo> getAllDocuments();

    /** List documents for a given user */
    List<DocInfo> getDocumentsByUser(Integer userID);

    /** Save a new or updated DocInfo */
    DocInfo save(DocInfo doc);

    /** Find by primary key */
    DocInfo findById(Integer docID);

    /** Delete by primary key */
    void deleteById(Integer docID);
}