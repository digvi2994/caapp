// src/main/java/com/erpca/erpcaapp/repository/DocInfoRepository.java
package com.erpca.erpcaapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.erpca.erpcaapp.model.DocInfo;

public interface DocInfoRepository extends JpaRepository<DocInfo, Integer> {

    /** 
     * If you want to retrieve all docs uploaded by a specific user:
     */
    List<DocInfo> findByUserID(Integer userID);

    // You can also add other query methods as needed, e.g.:
    // List<DocInfo> findByAlloflag(String alloflag);
}