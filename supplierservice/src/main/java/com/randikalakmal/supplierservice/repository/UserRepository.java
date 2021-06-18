package com.randikalakmal.supplierservice.repository;



import com.randikalakmal.supplierservice.model.UserType;
import com.randikalakmal.supplierservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmailAndUserType(String email, UserType userType);

    Optional<User> findByEmail(String email);
}
