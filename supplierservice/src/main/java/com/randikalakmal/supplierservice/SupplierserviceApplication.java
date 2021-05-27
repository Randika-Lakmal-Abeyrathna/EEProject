package com.randikalakmal.supplierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SupplierserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplierserviceApplication.class, args);
	}

}
