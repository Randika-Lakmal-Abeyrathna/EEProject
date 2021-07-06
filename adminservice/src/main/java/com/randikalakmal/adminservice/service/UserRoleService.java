package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.dto.UserRoleRequest;
import com.randikalakmal.adminservice.exception.UserRoleException;
import com.randikalakmal.adminservice.model.Role;
import com.randikalakmal.adminservice.model.User;
import com.randikalakmal.adminservice.model.UserRoles;
import com.randikalakmal.adminservice.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserService userService;
    private final RoleService roleService;

    public List<UserRoles> getAllUserRolesList(){
        return userRoleRepository.findAll();
    }

    public List<UserRoles> getUserRolesByUser(String email){
        User user = userService.getUserByEmail(email);
        return userRoleRepository.findByUser(user);
    }

    public List<UserRoles> getUserRolesByRole(String roleName){
        Role role = roleService.getRoleByName(roleName);
        return userRoleRepository.findByRole(role);
    }

    public UserRoles addUserRole(UserRoleRequest userRoleRequest){
        User user = userService.getUserByEmail(userRoleRequest.getUserEmail());
        Role role = roleService.getRoleByName(userRoleRequest.getRoleName());

        userRoleAddValidation(user,role);
        UserRoles userRoles =new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(role);
        return userRoleRepository.save(userRoles);

    }

    private int getCountByUserAndRole(User user, Role role){
        return userRoleRepository.countByUserAndRole(user,role);
    }

    private void userRoleAddValidation(User user, Role role) {

        if (getCountByUserAndRole(user,role)>0){
            throw new UserRoleException("User "+user.getEmail()+" already has Role "+role.getName());
        }

    }


}
