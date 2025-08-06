package com.erpca.erpcaapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

/**
 * AWS S3–backed implementation of FileStorageService.
 */
@Service
public class S3FileStorageService implements FileStorageService {

    private final S3Client s3Client;
    private final S3Presigner presigner;
    private final String bucketName;

    public S3FileStorageService(
        S3Client s3Client,
        S3Presigner presigner,
        @Value("${app.s3.bucket}") String bucketName
    ) {
        this.s3Client    = s3Client;
        this.presigner   = presigner;
        this.bucketName  = bucketName;
    }

    @Override
    public String upload(MultipartFile file, String fileName) {

    	//String key = "uploads/" + fileName;
        String key = "customers/" + fileName;

        System.out.println("File name key is ..."+fileName);

        Map<String,String> metadata = new HashMap<>();
        metadata.put("originalFilename", file.getOriginalFilename());
        metadata.put("contentType", file.getContentType());

        try (InputStream is = file.getInputStream()) {
        	
            // Determine Content-Type (prefer MultipartFile’s guess)
            String ct = file.getContentType();
            if (ct == null || ct.isBlank()) {
                ct = URLConnection.guessContentTypeFromName(fileName);
                if (ct == null) {
                    ct = "application/octet-stream";
                }
            }
            
            PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .metadata(metadata)
                .build();

            s3Client.putObject(putReq, RequestBody.fromInputStream(is, file.getSize()));
            return key;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    @Override
    public List<String> listFiles() {
        ListObjectsV2Request req = ListObjectsV2Request.builder()
            .bucket(bucketName)
            .prefix("uploads/")
            .build();
        
        ListObjectsV2Response resp = s3Client.listObjectsV2(req);

        return resp.contents()
                .stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

    @Override
    public String generatePresignedUrl(String key) {
    	
    	System.out.println("The selected file name is "+key);
    	
        GetObjectPresignRequest preq = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(15))
            .getObjectRequest(r -> r.bucket(bucketName).key(key))
            .build();

        PresignedGetObjectRequest presigned = presigner.presignGetObject(preq);
        
    	System.out.println("The generated URL is "+presigned.url().toExternalForm());
        
        return presigned.url().toExternalForm();
    }
    
    @Override
    public InputStream downloadFile(String key) {
        GetObjectRequest req = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();
        return s3Client.getObject(req);
    }

    @Override
    public String getContentType(String key) {
        try {
            HeadObjectRequest headReq = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            HeadObjectResponse headResp = s3Client.headObject(headReq);
            String ct = headResp.contentType();
            if (ct != null && !ct.isBlank()) {
                return ct;
            }
        } catch (NoSuchKeyException e) {
            // Key might not exist; let caller handle the missing file.
        }

        // Fallback by extension
        String ext = "";
        int idx = key.lastIndexOf('.');
        if (idx >= 0) {
            ext = key.substring(idx + 1).toLowerCase();
        }
        return switch (ext) {
            case "pdf"       -> "application/pdf";
            case "jpeg", "jpg" -> "image/jpeg";
            case "png"       -> "image/png";
            case "gif"       -> "image/gif";
            default          -> "application/octet-stream";
        };
    }

}
