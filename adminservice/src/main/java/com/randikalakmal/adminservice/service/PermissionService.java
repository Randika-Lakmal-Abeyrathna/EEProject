package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.exception.PermissionException;
import com.randikalakmal.adminservice.exception.RoleException;
import com.randikalakmal.adminservice.model.Permission;
import com.randikalakmal.adminservice.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<Permission> getAllPermissions(){
        return permissionRepository.findAll();
    }

    public Permission getPermissionByPermissionName(String permissionName){
        return permissionRepository.findByName(permissionName)
                .orElseThrow(()-> new PermissionException("Permission " +permissionName+" was not found"));
    }


    public Permission addPermission(Permission permission){
        permissionAddValidation(permission);
        return permissionRepository.save(permission);
    }

    private int getCountByPermission(String permissionName){
        return permissionRepository.countByPermission(permissionName);
    }

    private void permissionAddValidation(Permission permission) {
        String permissionName = permission.getPermission();

        if (permissionName.isEmpty() || permissionName.isBlank()){
            throw new RoleException("permission cannot be empty or blank");
        }
        if (getCountByPermission(permissionName)>0){
            throw new RoleException("Permission "+permissionName+" already exist !");
        }

    }


}
