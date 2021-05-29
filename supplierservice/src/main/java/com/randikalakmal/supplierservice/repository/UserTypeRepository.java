package com.randikalakmal.supplierservice.repository;

import com.randikalakmal.supplierservice.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType,Integer> {

    Optional<UserType> findUserTypeByUserType(String userType);
}
