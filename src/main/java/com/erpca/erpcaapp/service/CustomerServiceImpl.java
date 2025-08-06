package com.erpca.erpcaapp.service;

import com.erpca.erpcaapp.model.Customer;
import com.erpca.erpcaapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private S3Client s3;

    @Value("${app.s3.bucket}")
    private String bucket;

//    public CustomerServiceImpl(
////        CustomerRepository repo,
//        S3Client s3,
//        @Value("${app.s3.bucket}") String bucket
//    ) {

    /// /        this.repo   = repo;
//        this.s3     = s3;
//        this.bucket = bucket;
//    }
    @Override
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
    }

    @Override
    public Customer saveCustomer(Customer customer) {

        System.out.println("Currently in Save CustomerServiceImpl procedure...");
        // 1) Save to DB (so that ID is generated)
        Customer saved = repo.save(customer);

        // 2) Create “folder” in S3 (zero‐byte object whose key ends with ‘/’)
        String prefix = String.format("customers/%d/", saved.getCustID());
        PutObjectRequest por = PutObjectRequest.builder()
                .bucket(bucket)
                .key(prefix)
                .build();
        s3.putObject(por, RequestBody.empty());

        return saved;
    }

    @Override
    public void deleteCustomer(Long id) {
        repo.deleteById(id);
    }
}