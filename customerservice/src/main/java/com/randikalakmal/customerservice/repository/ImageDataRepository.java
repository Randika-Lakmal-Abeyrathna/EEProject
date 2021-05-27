package com.randikalakmal.customerservice.repository;

import com.randikalakmal.customerservice.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData,Integer> {
}
