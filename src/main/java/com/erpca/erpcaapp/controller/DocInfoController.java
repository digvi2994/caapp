// src/main/java/com/erpca/erpcaapp/controller/	
package com.erpca.erpcaapp.controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.erpca.erpcaapp.model.DocInfo;
import com.erpca.erpcaapp.service.DocInfoService;
import com.erpca.erpcaapp.service.FileStorageService;

@Controller
@RequestMapping("/docs")
public class DocInfoController {

    private final DocInfoService docService;
    private final FileStorageService fileStorageService;

    public DocInfoController(
            DocInfoService docService,
            FileStorageService fileStorageService
    ) {
        this.docService = docService;
        this.fileStorageService = fileStorageService;
    }

    /** 1) List all Docs */
    @GetMapping
    public String listDocs(Model model) {
        List<DocInfo> list = docService.getAllDocuments();
        model.addAttribute("docs", list);
        return "docinfo_list";   // Thymeleaf template to show table
    }

    /** 2) Show “create new” form */
    @GetMapping("/new")
    public String showCreateForm(@RequestParam("custID") Integer custID, Model model) {
        DocInfo doc = new DocInfo();
        LocalDate today = LocalDate.now();
        doc.setUploadDt(today);
        doc.setCompltionDt(today);
        System.out.println("Today date is "+today);
        System.out.println("Seleted Customer is "+custID);
        model.addAttribute("docInfo", doc);
        model.addAttribute("custID", custID);
        return "docinfo_form";   // Thymeleaf form for creating/editing
    }

    /** 3) Handle “create” submission, including S3 upload */
    @PostMapping
    public String createDoc(
            @ModelAttribute("docInfo") DocInfo docInfo,
            @RequestParam("file") MultipartFile file,
            @RequestParam("custID") Integer custID,      // ← grab the customer ID from the form/query
            BindingResult result,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            return "docinfo_form";
        }
        
        // 1) Set uploadDt & completionDt to what the user chose in the form:
        //    (Since your form already bound uploadDt/compltionDt via `th:field`,
        //     they are already populated on `docInfo`.)
        
        LocalDate today = LocalDate.now();
        System.out.println("Aajachi date is "+today);
        docInfo.getUploadDt();
        docInfo.getCompltionDt();
        System.out.println("Upload date is "+docInfo.getCompltionDt());
        
        System.out.println("upload docs for customer "+custID);

        // 2) Upload the file to S3
        String originalFilename = custID + "/" + file.getOriginalFilename();
        // ▶ You might prepend a folder or timestamp, e.g. "uploads/" + originalFilename
        
        System.out.println("Original FIle name is .."+originalFilename);
        System.out.println("MultipartFile file name is .."+file);
        
        String s3Key = fileStorageService.upload(file, originalFilename);

        // 2) Store that key (or URL) in the DocInfo object.
        //    Here we overwrite docName to hold the S3 key. 
        //    If you have a dedicated column (e.g. "fileKey"), set that instead.
        docInfo.setDocName(s3Key);
        
        // 3) Set the Customer ID against this document.
        docInfo.setCustID(custID);

        // 4) Set the userID from session (or however you track “logged‐in user”)
        Integer currentUserID = (Integer) session.getAttribute("LOGGED_IN_USER_ID");
        if (currentUserID == null) {
            // Not logged in, redirect, etc.
            return "redirect:/login";
        }
        docInfo.setUserID(currentUserID);

        // 5) Finally, save the entity (which now has docName→S3Key, dates, flag, userID)
        docService.save(docInfo);

        return "redirect:/docs";
    }

    /** 4) Show “edit” form */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        DocInfo doc = docService.findById(id);
        if (doc == null) {
            return "redirect:/docs";
        }
        model.addAttribute("docInfo", doc);
        return "docinfo_form";
    }

    /** 5) Handle “update” submission */
    @PostMapping("/update/{id}")
    public String updateDoc(
            @PathVariable("id") Integer id,
            @ModelAttribute("docInfo") DocInfo docInfo,
            BindingResult result,
            Model model,
            HttpSession session   // again we need session
    ) {
        if (result.hasErrors()) {
            return "docinfo_form";
        }

        // *** Make sure userID stays set to whoever is logged in ***
        Integer currentUserID = (Integer) session.getAttribute("LOGGED_IN_USER_ID");
        if (currentUserID == null) {
            return "redirect:/login";
        }
        docInfo.setDocID(id);
        docInfo.setUserID(currentUserID);

        docService.save(docInfo);
        return "redirect:/docs";
    }
    
    /**
     *  New endpoint: Bulk‐insert selected S3 files into DocInfo table.
     */
    @PostMapping("/select")
    public String handleSelection(
        @RequestParam(value = "selectedFiles", required = false) List<String> selectedFiles,
        HttpSession session,    // or however you get the logged-in user
        RedirectAttributes ra
    ) {
    	
        System.out.println("Handlin Selectoins is.... ");
        // *** Make sure userID stays set to whoever is logged in ***
        Integer currentUserID = (Integer) session.getAttribute("LOGGED_IN_USER_ID");
        if (currentUserID == null) {
            return "redirect:/login";
        }
        
        System.out.println("Selected fles Selectoins is.... "+selectedFiles);
        
        if (selectedFiles == null || selectedFiles.isEmpty()) {
            ra.addFlashAttribute("error", "Please select at least one file.");
            return "redirect:/";
        }

        for (String s3Key : selectedFiles) {
            String filename = s3Key.substring(s3Key.lastIndexOf('/') + 1);
            
            System.out.println("Selected file name is "+filename);

            // 1) Optionally download the file or stream it if you need metadata
            // 2) Insert a new DocInfo record:
            DocInfo docInfo = new DocInfo();
            //docInfo.setDocID(id);
            docInfo.setUserID(currentUserID);
            docInfo.setDocName(filename);
            docInfo.setUploadDt(LocalDate.now());
            docInfo.setCompltionDt(LocalDate.now()); 
            docInfo.setUserID(currentUserID);
            // e) Persist
            //docService.save(docInfo);
        }

        ra.addFlashAttribute("success", "Selected files have been recorded.");
        return "redirect:/docs";
    }

    /** 6) Delete an entry */
    @GetMapping("/delete/{id}")
    public String deleteDoc(@PathVariable("id") Integer id) {
        docService.deleteById(id);
        return "redirect:/docs";
    }
}