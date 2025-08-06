package com.erpca.erpcaapp.controller;

import com.erpca.erpcaapp.service.FileStorageService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListFile {

    private final FileStorageService storageService;

    public ListFile(FileStorageService storageService) {
        this.storageService = storageService;
    }

    /**
     *  GET /files
     *  Fetch all S3 keys and pass to the view.
     */
    @GetMapping("/files")
    public String listFiles(Model model) {
        List<String> keys = storageService.listFiles();
        model.addAttribute("files", keys);
        return "files";   // resolves to templates/files.html
    }
}

