package com.randikalakmal.supplierservice.service;

import com.randikalakmal.supplierservice.dto.SupplierFeedbackRequest;
import com.randikalakmal.supplierservice.dto.SupplierFeedbackUpdateRequest;
import com.randikalakmal.supplierservice.model.SupplierFeedback;
import com.randikalakmal.supplierservice.model.SupplierInfo;
import com.randikalakmal.supplierservice.repository.SupplierFeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupplierFeedbackService {

    private final SupplierFeedbackRepository supplierFeedbackRepository;
    private final SupplierService supplierService;

    public List<SupplierFeedback> getAllSupplierFeedback() {
        return supplierFeedbackRepository.findAll();
    }

    public List<SupplierFeedback> getFeedBackBySupplierId(Integer supplierId) {
        SupplierInfo supplierInfo = supplierService.getSupplierById(supplierId);
        return supplierFeedbackRepository.findBySupplierInfo(supplierInfo);
    }

    public SupplierFeedback addSupplierFeedback(SupplierFeedbackRequest supplierFeedbackRequest) {
        String feedback = supplierFeedbackRequest.getFeedback();
        int rating = supplierFeedbackRequest.getRating();
        SupplierInfo supplierInfo = supplierService.getSupplierById(supplierFeedbackRequest.getSupplierInfoId());
        SupplierFeedback supplierFeedback = new SupplierFeedback();
        supplierFeedback.setFeedback(feedback);
        supplierFeedback.setRating(rating);
        supplierFeedback.setSupplierInfo(supplierInfo);

        return supplierFeedbackRepository.save(supplierFeedback);
    }

    public SupplierFeedback updateSupplierFeedback(SupplierFeedbackUpdateRequest supplierFeedbackUpdateRequest) {
        String feedback = supplierFeedbackUpdateRequest.getFeedback();
        int rating = supplierFeedbackUpdateRequest.getRating();
        SupplierInfo supplierInfo = supplierService.getSupplierById(supplierFeedbackUpdateRequest.getSupplierInfoId());
        SupplierFeedback supplierFeedback = supplierFeedbackRepository.getById(supplierFeedbackUpdateRequest.getId());
        supplierFeedback.setFeedback(feedback);
        supplierFeedback.setRating(rating);
        supplierFeedback.setSupplierInfo(supplierInfo);

        return supplierFeedbackRepository.save(supplierFeedback);

    }

    public void deleteSupplierFeedback(Integer feedbackId) {
        SupplierFeedback supplierFeedback = supplierFeedbackRepository.getById(feedbackId);
        supplierFeedbackRepository.delete(supplierFeedback);
    }

}
