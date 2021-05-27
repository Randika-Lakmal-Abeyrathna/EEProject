package com.randikalakmal.customerservice.repository;

import com.randikalakmal.customerservice.model.BrandImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandImageRepository extends JpaRepository<BrandImage,Integer>{
}
