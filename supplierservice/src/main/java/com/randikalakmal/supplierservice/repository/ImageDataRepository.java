package com.randikalakmal.supplierservice.repository;

import com.randikalakmal.supplierservice.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData,Integer> {
}
