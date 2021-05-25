package com.randikalakmal.adminservice.repository;

import com.randikalakmal.adminservice.model.BrandImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandImageRepository extends JpaRepository<BrandImage,Integer>{
}
