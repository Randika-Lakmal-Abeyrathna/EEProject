package com.randikalakmal.adminservice.repository;

import com.randikalakmal.adminservice.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData,Integer> {
}
