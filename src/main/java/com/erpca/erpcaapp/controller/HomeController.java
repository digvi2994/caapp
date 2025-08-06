// src/main/java/com/erpca/erpcaapp/controller/HomeController.java
package com.erpca.erpcaapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erpca.erpcaapp.service.FileStorageService;

@Controller
public class HomeController {

    private final FileStorageService storageService;

    public HomeController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Fetch all S3 keys under “uploads/”
        List<String> keys = storageService.listFiles();
        model.addAttribute("files", keys);
        return "index";  // corresponds to src/main/resources/templates/index.html
    }
}