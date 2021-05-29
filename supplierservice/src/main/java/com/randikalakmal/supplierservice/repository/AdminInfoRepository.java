package com.randikalakmal.supplierservice.repository;

import com.randikalakmal.supplierservice.model.AdminInfo;
import com.randikalakmal.supplierservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminInfoRepository extends JpaRepository<AdminInfo,Integer> {

    Optional<AdminInfo> getByUser(User user);

     Integer countByUser(User user);
}
