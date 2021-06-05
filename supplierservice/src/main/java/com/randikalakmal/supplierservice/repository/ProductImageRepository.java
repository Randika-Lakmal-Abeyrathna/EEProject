package com.randikalakmal.supplierservice.repository;

import com.randikalakmal.supplierservice.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Integer> {
}
