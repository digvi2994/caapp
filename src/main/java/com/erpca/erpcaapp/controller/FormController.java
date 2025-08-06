package com.erpca.erpcaapp.controller;

import com.erpca.erpcaapp.model.JournalForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forms")
public class FormController {

    @GetMapping("/sales with item sample")
    public String getSalesWithItemSampleForm() {
        return "sales_form";
    }

    @GetMapping("/bank statement")
    public String getBankStatementForm() {
        return "bank_statement_form";
    }

    @GetMapping("/journal form")
    public String getJournalForm(Model model) {
        model.addAttribute("journalForm", new JournalForm());
        return "journal_form";
    }
    @GetMapping("/purchase form")
    public String getPurchaseForm() {
        return "purchase_form";
    }
    @GetMapping("/item form")
    public String getItemForm() {
        return "item_form";
    }
}
