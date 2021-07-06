package com.randikalakmal.adminservice.repository;

import com.randikalakmal.adminservice.model.Role;
import com.randikalakmal.adminservice.model.User;
import com.randikalakmal.adminservice.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles,Integer> {

    List<UserRoles> findByUser(User user);

    List<UserRoles> findByRole(Role role);

    int countByUserAndRole(User user, Role role);
}
