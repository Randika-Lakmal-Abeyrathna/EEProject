package com.randikalakmal.supplierservice.repository;

import com.randikalakmal.supplierservice.model.BrandImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandImageRepository extends JpaRepository<BrandImage,Integer>{
}
