package com.randikalakmal.supplierservice.controller;

import com.randikalakmal.supplierservice.dto.SupplierInfoRequest;
import com.randikalakmal.supplierservice.model.SupplierInfo;
import com.randikalakmal.supplierservice.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/supplier/")
@AllArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('supplier:read')")
    public ResponseEntity<List<SupplierInfo>> getAllSuppliers(){
        List<SupplierInfo> supplierInfoList = supplierService.getAllSupplier();
        return new ResponseEntity<>(supplierInfoList, HttpStatus.OK);
    }

    @GetMapping("/find/{email}")
    @PreAuthorize("hasAuthority('supplier:read')")
    public ResponseEntity<SupplierInfo> getSupplierByUserEmail(@PathVariable("email") String email){
        SupplierInfo supplierInfo = supplierService.getSupplierByUserEmail(email);
        return new ResponseEntity<>(supplierInfo,HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('supplier:read')")
    public ResponseEntity<SupplierInfo> getSupplierById(@PathVariable("id") Integer id){
        SupplierInfo supplierInfo =supplierService.getSupplierById(id);
        return new ResponseEntity<>(supplierInfo,HttpStatus.OK);
    }


    @PostMapping("/update/image")
    @PreAuthorize("hasAnyAuthority('supplier:edit','supplier:write')")
    public ResponseEntity<?> updateSupplierImage(@RequestParam("email") String email, MultipartFile image) throws IOException {
        supplierService.updateSupplierImage(email,image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('supplier:write')")
    public ResponseEntity<SupplierInfo> addSupplier(@RequestBody SupplierInfoRequest supplierInfoRequest){
        SupplierInfo supplierInfo = supplierService.addSupplier(supplierInfoRequest);
        return new ResponseEntity<>(supplierInfo,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('supplier:edit')")
    public ResponseEntity<SupplierInfo> updateCustomer(@RequestBody SupplierInfoRequest supplierInfoRequest){
        SupplierInfo supplierInfo = supplierService.updateSupplierInfo(supplierInfoRequest);
        return new ResponseEntity<>(supplierInfo,HttpStatus.OK);
    }



}
