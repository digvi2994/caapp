// src/main/java/com/erpca/erpcaapp/service/DocInfoServiceImpl.java
package com.erpca.erpcaapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erpca.erpcaapp.model.DocInfo;
import com.erpca.erpcaapp.repository.DocInfoRepository;

@Service
@Transactional
public class DocInfoServiceImpl implements DocInfoService {

    private final DocInfoRepository docRepo;

    public DocInfoServiceImpl(DocInfoRepository docRepo) {
        this.docRepo = docRepo;
    }

    @Override
    public List<DocInfo> getAllDocuments() {
        return docRepo.findAll();
    }

    @Override
    public List<DocInfo> getDocumentsByUser(Integer userID) {
        return docRepo.findByUserID(userID);
    }

    @Override
    public DocInfo save(DocInfo doc) {
        return docRepo.save(doc);
    }

    @Override
    public DocInfo findById(Integer docID) {
        return docRepo.findById(docID).orElse(null);
    }

    @Override
    public void deleteById(Integer docID) {
        docRepo.deleteById(docID);
    }
}