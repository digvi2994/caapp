package com.erpca.erpcaapp.controller;

import com.erpca.erpcaapp.service.FileStorageService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewFile {

    private final FileStorageService storageService;

    public ViewFile(FileStorageService storageService) {
        this.storageService = storageService;
    }

    /**
     *  GET /view/{key}
     *  Generates a presigned URL and infers the file extension for the template.
     */
    @GetMapping("/view/{*key}")
    public String viewFile(@PathVariable String key, Model model) {
    	
    	System.out.println("The key is "+key);
    	
        // ensure no leading slash
        String normalizedKey = key.startsWith("/") ? key.substring(1) : key;
    	    	
        String url = storageService.generatePresignedUrl(normalizedKey);
        
        System.out.println("The generated Presigned URL key is "+url);
        
//        System.out.println("The hardcoded Presigned URL key is "+url);
        
        // Derive extension for conditional rendering in Thymeleaf
        String ext = "";
        int idx = key.lastIndexOf('.');
        if (idx > 0 && idx < key.length() - 1) {
            ext = key.substring(idx + 1).toLowerCase();
        }

        model.addAttribute("fileUrl", url);
        model.addAttribute("fileExt", ext);
        return "view";    // resolves to src/main/resources/templates/view.html
    }
}

