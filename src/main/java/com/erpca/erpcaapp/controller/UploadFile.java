package com.erpca.erpcaapp.controller;

import com.erpca.erpcaapp.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadFile {

    private final FileStorageService storageService;

    @Autowired
    public UploadFile(FileStorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * 1. Show the empty upload form.
     *    URL:  GET /upload
     */
    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        // If you want to display any initial data or messages, add them here:
        // model.addAttribute("title", "Upload a file");
        return "upload";   // renders src/main/resources/templates/upload.html
    }

    /**
     * 2. Handle the form submission.
     *    URL:  POST /upload
     */
    @PostMapping("/upload")
    public String handleFileUpload(
            @RequestParam("fileName") String fileName,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttrs
    ) {
        try {
        	
        	System.out.println("File Name is "+fileName);
            // Delegate to your FileStorageService:
            String key = storageService.upload(file, fileName);

            // Prepare a success message for display on redirect:
            redirectAttrs.addFlashAttribute("successMessage",
                    "Your file was uploaded successfully as “" + key + "”.");

        } catch (Exception e) {
            // In production, catch more specific exceptions and log them.
            redirectAttrs.addFlashAttribute("errorMessage",
                    "Upload failed: " + e.getMessage());
        }

        // Redirect to the list page (or back to /upload):
        return "redirect:/";
    }
}

