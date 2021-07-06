package com.randikalakmal.adminservice.repository;

import com.randikalakmal.adminservice.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission,Integer> {

    List<RolePermission> findByPermission(Permission permission);

    List<RolePermission> findByRole(Role role);

    int countByPermissionAndRole(Permission permission, Role role);
}
