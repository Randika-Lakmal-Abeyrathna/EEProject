package com.randikalakmal.customerservice.repository;

import com.randikalakmal.customerservice.model.CustomerInfo;
import com.randikalakmal.customerservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo,Integer> {

    Optional<CustomerInfo> findByUser(User user);

    Integer countByUser(User user);
}
