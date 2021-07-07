package com.randikalakmal.supplierservice.repository;

import com.randikalakmal.supplierservice.model.SupplierFeedback;
import com.randikalakmal.supplierservice.model.SupplierInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierFeedbackRepository extends JpaRepository<SupplierFeedback,Integer> {

    List<SupplierFeedback> findBySupplierInfo(SupplierInfo supplierInfo);
}
