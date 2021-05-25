package com.randikalakmal.adminservice.repository;

import com.randikalakmal.adminservice.model.AdminInfo;
import com.randikalakmal.adminservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminInfoRepository extends JpaRepository<AdminInfo,Integer> {

    Optional<AdminInfo> getByUser(User user);

     Integer countByUser(User user);
}
