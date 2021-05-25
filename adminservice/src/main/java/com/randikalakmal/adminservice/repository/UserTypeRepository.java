package com.randikalakmal.adminservice.repository;

import com.randikalakmal.adminservice.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType,Integer> {

    Optional<UserType> findUserTypeByUserType(String userType);
}
