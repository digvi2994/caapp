package com.erpca.erpcaapp.service;

import com.erpca.erpcaapp.model.JournalForm;
import com.erpca.erpcaapp.repository.JournalFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalFormService {
    @Autowired
    private JournalFormRepository journalFormRepository;
    public void saveJournal(JournalForm dto) {
        // Map DTO to Entity and save to DB
//        Journal journal = new Journal();
//
//        journal.setJournalNo(dto.getJournalNo());
//        journal.setReferenceNo(dto.getReferenceNo());
//        journal.setDate(dto.getDate());
//        journal.setCostCenter(dto.getCostCenter());
//        journal.setParticulars(dto.getParticulars());
//        journal.setItemName(dto.getItemName());
//        journal.setQuantity(dto.getQuantity());
//        journal.setRate(dto.getRate());
//        journal.setDrCr(dto.getDrCr());
//        journal.setAmount(dto.getAmount());
//        journal.setLedgerNarration(dto.getLedgerNarration());
//        journal.setNarration(dto.getNarration());
//        journal.setAmount1(dto.getAmount1());
//        journal.setDrCr1(dto.getDrCr1());
//        journal.setLedgerNarration1(dto.getLedgerNarration1());
//        journal.setAmount2(dto.getAmount2());
//        journal.setDrCr2(dto.getDrCr2());
//        journal.setLedgerNarration2(dto.getLedgerNarration2());
//        journal.setCustomerId(dto.getCustomerId());
//        journal.setUserId(dto.getUserId());

        // save to DB via JPA Repository
        journalFormRepository.save(dto);
    }
}
