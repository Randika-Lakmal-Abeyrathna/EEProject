package com.randikalakmal.supplierservice.controller;

import com.randikalakmal.supplierservice.dto.SupplierFeedbackRequest;
import com.randikalakmal.supplierservice.dto.SupplierFeedbackUpdateRequest;
import com.randikalakmal.supplierservice.model.SupplierFeedback;
import com.randikalakmal.supplierservice.service.SupplierFeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier/feedback")
@AllArgsConstructor
public class SupplierFeedbackController {

    private final SupplierFeedbackService supplierFeedbackService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('supplierfeedback:read')")
    public ResponseEntity<List<SupplierFeedback>> getAllSupplierFeed(){
        List<SupplierFeedback> supplierFeedbackList = supplierFeedbackService.getAllSupplierFeedback();
        return new ResponseEntity<>(supplierFeedbackList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('supplierfeedback:read')")
    public ResponseEntity<List<SupplierFeedback>> getSupplierFeedbackBySupplierId(@PathVariable("id") Integer supplierId){
        List<SupplierFeedback> supplierFeedbackList = supplierFeedbackService.getFeedBackBySupplierId(supplierId);
        return new ResponseEntity<>(supplierFeedbackList,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('supplierfeedback:write')")
    public ResponseEntity<SupplierFeedback> addSupplierFeedback(@RequestBody SupplierFeedbackRequest supplierFeedbackRequest){
        SupplierFeedback supplierFeedback = supplierFeedbackService.addSupplierFeedback(supplierFeedbackRequest);
        return new ResponseEntity<>(supplierFeedback,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('supplierfeedback:edit')")
    public ResponseEntity<SupplierFeedback> updateSupplierFeedback
            (@RequestBody SupplierFeedbackUpdateRequest supplierFeedbackUpdateRequest){
        SupplierFeedback supplierFeedback = supplierFeedbackService.updateSupplierFeedback(supplierFeedbackUpdateRequest);
        return new ResponseEntity<>(supplierFeedback,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('supplierfeedback:delete')")
    public ResponseEntity<?> deleteSupplierFeedback(@PathVariable("id") Integer feedBackId){
        supplierFeedbackService.deleteSupplierFeedback(feedBackId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
