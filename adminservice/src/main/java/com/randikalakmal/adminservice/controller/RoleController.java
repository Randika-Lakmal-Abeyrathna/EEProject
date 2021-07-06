package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.model.Role;
import com.randikalakmal.adminservice.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/role")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('role:read')")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roleList = roleService.getAllRoles();
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @GetMapping("/find/{name}")
    @PreAuthorize("hasAuthority('role:read')")
    public ResponseEntity<Role> getRoleByRoleName(@PathVariable("name") String roleName){
        Role role = roleService.getRoleByName(roleName);
        return new ResponseEntity<>(role,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('role:write')")
    public ResponseEntity<Role> addRole(@RequestBody Role role){
        Role addedRole = roleService.addRole(role);
        return new ResponseEntity<>(addedRole,HttpStatus.CREATED);
    }

}
