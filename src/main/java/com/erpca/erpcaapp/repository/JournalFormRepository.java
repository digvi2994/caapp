package com.erpca.erpcaapp.repository;

import com.erpca.erpcaapp.model.JournalForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalFormRepository extends JpaRepository<JournalForm,Long> {

}
