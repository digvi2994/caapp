package com.erpca.erpcaapp.controller;

import java.io.InputStream;
import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.erpca.erpcaapp.service.FileStorageService;

@RestController
public class FileProxyController {

    private final FileStorageService storageService;

    public FileProxyController(FileStorageService storageService) {
        this.storageService = storageService;
    }

  /**
   * Streams the S3 object at “key” inline so the browser (or Acrobat plugin)
   * can open it. E.g. GET /proxy?key=uploads/TestFile.pdf
   */
  @GetMapping("/proxy")
  public void proxyFile(
          @RequestParam("key") String key,
          HttpServletResponse response
  ) throws IOException {
      try {
    	  
      	  System.out.println("The proxy key is "+key);
      	
          // 1) Look up or derive the correct Content-Type (e.g. “application/pdf”)
          String contentType = storageService.getContentType(key);
          
      	  System.out.println("The Content type is "+contentType);
      	
          response.setContentType(contentType);

          // 2) Instruct “inline” display (don’t force download)
          String filename = key.substring(key.lastIndexOf('/') + 1);
          
      	  System.out.println("The file name is "+filename);
          
          response.setHeader(
              "Content-Disposition",
              "inline; filename=\"" + filename + "\""
          );
          
    	  System.out.println("After setting Header "+key);

          // 3) Stream the raw bytes from S3 → browser
          try (InputStream is = storageService.downloadFile(key)) {

        	  System.out.println("In try method "+key);

              is.transferTo(response.getOutputStream());
          }
          catch (Exception ex) {

        	  System.out.println("In exception method "+filename);

              // If the key is missing or any error, return 404
              response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
          }
      } catch (Exception ex) {
          // If the key is missing or any error, return 404
          response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
      }
  }
}