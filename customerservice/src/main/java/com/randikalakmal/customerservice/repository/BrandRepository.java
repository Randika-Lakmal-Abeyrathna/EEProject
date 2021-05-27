package com.randikalakmal.customerservice.repository;

import com.randikalakmal.customerservice.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {

    Optional<Brand> findBrandByBrandname(String brandName);

    int countByBrandname(String brandname);

    List<Brand> findByBrandnameIgnoreCaseContaining(String brandName);

}

