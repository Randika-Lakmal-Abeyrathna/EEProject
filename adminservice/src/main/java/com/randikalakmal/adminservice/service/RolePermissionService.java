package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.dto.RolePermissionRequest;
import com.randikalakmal.adminservice.exception.RolePermissionException;
import com.randikalakmal.adminservice.exception.UserRoleException;
import com.randikalakmal.adminservice.model.*;
import com.randikalakmal.adminservice.repository.RolePermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionService permissionService;
    private final RoleService roleService;

    public List<RolePermission> getAllRolePermissions(){
        return rolePermissionRepository.findAll();
    }

    public List<RolePermission> getRolePermissionByRoleName(String roleName){
        Role role =roleService.getRoleByName(roleName);
        return rolePermissionRepository.findByRole(role);
    }

    public List<RolePermission> getRolePermissionByPermissionName(String permissionName){
        Permission permission = permissionService.getPermissionByPermissionName(permissionName);
        return rolePermissionRepository.findByPermission(permission);
    }


    public RolePermission addRolePermission(RolePermissionRequest rolePermissionRequest){
        Role role = roleService.getRoleByName(rolePermissionRequest.getRoleName());
        Permission permission = permissionService.getPermissionByPermissionName(rolePermissionRequest.getPermissionName());

        rolePermissionAddValidation(permission,role);
        RolePermission rolePermission =new RolePermission();
        rolePermission.setPermission(permission);
        rolePermission.setRole(role);
        return rolePermissionRepository.save(rolePermission);

    }

    private int getCountByPermissionAndRole(Permission permission, Role role){
        return rolePermissionRepository.countByPermissionAndRole(permission,role);
    }

    private void rolePermissionAddValidation(Permission permission, Role role) {

        if (getCountByPermissionAndRole(permission,role)>0){
            throw new RolePermissionException("Role "+role.getName()+" already has Permission "+permission.getPermission());
        }

    }


}
