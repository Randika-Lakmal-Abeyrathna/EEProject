package com.randikalakmal.adminservice.repository;


import com.randikalakmal.adminservice.model.User;
import com.randikalakmal.adminservice.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmailAndUserType(String email, UserType userType);
}
