package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.exception.RoleException;
import com.randikalakmal.adminservice.model.Role;
import com.randikalakmal.adminservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Role getRoleByName(String roleName){
        return roleRepository.findByName(roleName)
                .orElseThrow(()-> new RoleException("Role name "+roleName+" not found"));
    }

    public Role addRole(Role role){
        roleAddValidation(role);
        return roleRepository.save(role);
    }

    private int getCountByRole(String roleName){
        return roleRepository.countByRole(roleName);
    }

    private void roleAddValidation(Role role) {
        String roleName = role.getName();

        if (roleName.isEmpty() || roleName.isBlank()){
            throw new RoleException("Role Name cannot be empty or blank");
        }
        if (getCountByRole(roleName)>0){
            throw new RoleException("Role "+roleName+" already exist !");
        }

    }

}
