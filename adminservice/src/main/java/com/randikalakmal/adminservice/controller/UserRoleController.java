package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.dto.UserRoleRequest;
import com.randikalakmal.adminservice.model.UserRoles;
import com.randikalakmal.adminservice.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/userrole")
@AllArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('userrole:read')")
    public ResponseEntity<List<UserRoles>> getAllUserRoles(){
        List<UserRoles> userRolesList = userRoleService.getAllUserRolesList();
        return new ResponseEntity<>(userRolesList, HttpStatus.OK);
    }

    @GetMapping("/find/user/{name}")
    @PreAuthorize("hasAuthority('userrole:read')")
    public ResponseEntity<List<UserRoles>> getAllUserRolesByUser(@PathVariable("name") String userEmail){
        List<UserRoles> userRolesList = userRoleService.getUserRolesByUser(userEmail);
        return new ResponseEntity<>(userRolesList,HttpStatus.OK);
    }

    @GetMapping("/find/role/{name}")
    @PreAuthorize("hasAuthority('userrole:read')")
    public ResponseEntity<List<UserRoles>> getAllUserRolesByRole(@PathVariable("name") String roleName){
        List<UserRoles> userRolesList = userRoleService.getUserRolesByRole(roleName);
        return new ResponseEntity<>(userRolesList,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('userrole:write')")
    public ResponseEntity<UserRoles> addUserRole(@RequestBody UserRoleRequest userRoleRequest){
        UserRoles addedUserRole = userRoleService.addUserRole(userRoleRequest);
        return new ResponseEntity<>(addedUserRole,HttpStatus.CREATED);
    }

}
