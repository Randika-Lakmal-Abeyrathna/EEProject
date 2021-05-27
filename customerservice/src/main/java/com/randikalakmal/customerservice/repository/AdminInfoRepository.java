package com.randikalakmal.customerservice.repository;


import com.randikalakmal.customerservice.model.User;
import com.randikalakmal.customerservice.model.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminInfoRepository extends JpaRepository<AdminInfo,Integer> {

    Optional<AdminInfo> getByUser(User user);

     Integer countByUser(User user);
}
