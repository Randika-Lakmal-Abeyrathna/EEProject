package com.randikalakmal.supplierservice.repository;

import com.randikalakmal.supplierservice.model.SupplierInfo;
import com.randikalakmal.supplierservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierInfoRepository extends JpaRepository<SupplierInfo,Integer> {

    Optional<SupplierInfo> findByUser(User user);


}
