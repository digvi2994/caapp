package com.erpca.erpcaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	    "com.erpca.erpcaapp",      	// your own code
	    "com.erpca.erpcaapp.config" // AwsS3Config config 
	})
public class ErpcaappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpcaappApplication.class, args);
	}

}
