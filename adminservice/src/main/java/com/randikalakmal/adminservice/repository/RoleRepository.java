package com.randikalakmal.adminservice.repository;

import com.randikalakmal.adminservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(String roleName);

    int countByRole(String roleName);

}
