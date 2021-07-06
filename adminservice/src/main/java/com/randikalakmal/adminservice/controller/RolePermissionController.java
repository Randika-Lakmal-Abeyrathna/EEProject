package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.dto.RolePermissionRequest;
import com.randikalakmal.adminservice.model.RolePermission;
import com.randikalakmal.adminservice.service.RolePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/rolepermission")
@AllArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('rolepermission:read')")
    public ResponseEntity<List<RolePermission>> getAllRolePermissions(){
        List<RolePermission> rolePermissions = rolePermissionService.getAllRolePermissions();
        return new ResponseEntity<>(rolePermissions, HttpStatus.OK);
    }

    @GetMapping("/find/role/{name}")
    @PreAuthorize("hasAuthority('rolepermission:read')")
    public ResponseEntity<List<RolePermission>> getRolePermissionsByRoleName(@PathVariable("name") String roleName){
        List<RolePermission> rolePermissions = rolePermissionService.getRolePermissionByRoleName(roleName);
        return new ResponseEntity<>(rolePermissions,HttpStatus.OK);
    }

    @GetMapping("/find/permission/{name}")
    @PreAuthorize("hasAuthority('rolepermission:read')")
    public ResponseEntity<List<RolePermission>> getRolePermissionsByPermissionName(@PathVariable("name")String permissionName){
        List<RolePermission> rolePermissions = rolePermissionService.getRolePermissionByPermissionName(permissionName);
        return new ResponseEntity<>(rolePermissions,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('rolepermission:write')")
    public ResponseEntity<RolePermission> addRolePermission(@RequestBody RolePermissionRequest rolePermissionRequest){
        RolePermission addedRolePermission = rolePermissionService.addRolePermission(rolePermissionRequest);
        return new ResponseEntity<>(addedRolePermission,HttpStatus.CREATED);
    }

}
