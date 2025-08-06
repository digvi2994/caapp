package com.erpca.erpcaapp.controller;

import com.erpca.erpcaapp.model.JournalForm;
import com.erpca.erpcaapp.service.JournalFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class JournalFormController {


    //        private final JournalService journalService;
//
//        public JournalController(JournalService journalService) {
//            this.journalService = journalService;
//        }
    @Autowired
    private JournalFormService journalFormService;

    @PostMapping("/submitJournal")
    public String submitJournal(@ModelAttribute("journalForm") JournalForm journalForm) {
        journalFormService.saveJournal(journalForm);
        return "redirect:/success"; // Redirect to a success page
    }

}
