package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.model.Permission;
import com.randikalakmal.adminservice.model.Role;
import com.randikalakmal.adminservice.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/permission")
@AllArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('permission:read')")
    public ResponseEntity<List<Permission>> getAllPermission(){
        List<Permission> permissionList = permissionService.getAllPermissions();
        return new ResponseEntity<>(permissionList, HttpStatus.OK);
    }

    @GetMapping("/find/{name}")
    @PreAuthorize("hasAuthority('permission:read')")
    public ResponseEntity<Permission> getPermissionByPermissionName(@PathVariable("name") String permissionName){
        Permission permission = permissionService.getPermissionByPermissionName(permissionName);
        return new ResponseEntity<>(permission,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<Permission> addPermission(@RequestBody Permission permission){
        Permission addedPermission = permissionService.addPermission(permission);
        return new ResponseEntity<>(addedPermission,HttpStatus.CREATED);
    }

}
