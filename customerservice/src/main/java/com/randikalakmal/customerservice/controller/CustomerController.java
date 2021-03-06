package com.randikalakmal.customerservice.controller;

import com.randikalakmal.customerservice.dto.CustomerRequest;
import com.randikalakmal.customerservice.model.CustomerInfo;
import com.randikalakmal.customerservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('customer:read')")
    public ResponseEntity<List<CustomerInfo>> getAllCustomer(){
        List<CustomerInfo> customerInfoList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerInfoList,HttpStatus.OK);
    }

    @GetMapping("/find/{email}")
    @PreAuthorize("hasAuthority('customer:read')")
    public ResponseEntity<CustomerInfo> getCustomerByUserEmail(@PathVariable("email") String email){
        CustomerInfo customerInfo = customerService.findCustomerByUserEmail(email);
        return new ResponseEntity<>(customerInfo,HttpStatus.OK);
    }

    @PostMapping("/update/image")
    @PreAuthorize("hasAnyAuthority('customer:edit','customer:write')")
    public ResponseEntity<?> updateCustomerImage(@RequestParam("email") String email, MultipartFile image) throws IOException {
        customerService.updateCustomerImage(email,image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('customer:write')")
    public ResponseEntity<CustomerInfo> addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerInfo customerInfo = customerService.addCustomer(customerRequest);
        return new ResponseEntity<>(customerInfo,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('customer:edit')")
    public ResponseEntity<CustomerInfo> updateCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerInfo customerInfo = customerService.updateCustomer(customerRequest);
        return new ResponseEntity<>(customerInfo,HttpStatus.OK);
    }

}
