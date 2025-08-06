package com.erpca.erpcaapp.service;

import java.io.InputStream;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * Defines operations for storing files and retrieving metadata.
 */
public interface FileStorageService {
    /**
     * Upload a multipart file under a logical name.
     * @param file incoming file
     * @param fileName desired logical filename (e.g. "doc.pdf")
     * @return the S3 key where the file is stored (e.g. "uploads/doc.pdf")
     */
    String upload(MultipartFile file, String fileName);

    /**
     * List all stored file keys under the uploads prefix.
     * @return list of S3 object keys
     */
    List<String> listFiles();

    /**
     * Generate a time-limited presigned URL for viewing.
     * @param key the S3 object key
     * @return presigned URL string
     */
    String generatePresignedUrl(String key);
    
    // NEW: stream raw bytes
    InputStream downloadFile(String key);

    // NEW: get MIME type
    String getContentType(String key);
}