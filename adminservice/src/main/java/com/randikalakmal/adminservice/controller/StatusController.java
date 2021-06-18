package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.exception.StatusException;
import com.randikalakmal.adminservice.model.Status;
import com.randikalakmal.adminservice.service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/status")
@AllArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('status:read')")
    public ResponseEntity<List<Status>> getAllStatus(){
        List<Status> statusList = statusService.getAllStatus();
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('status:read')")
    public ResponseEntity<Status> getStatusById(@PathVariable Integer id){
        Status status = statusService.getStatusById(id);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    @PreAuthorize("hasAuthority('status:read')")
    public ResponseEntity<Status> getStatusByStatus(@PathVariable String name){
        Status statusList = statusService.findStatusByStatus(name);
        return new ResponseEntity<>(statusList,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('status:write')")
    public ResponseEntity<Status> addStatus(@RequestBody Status status){
        Status newStatus = statusService.addStatus(status);
        return new ResponseEntity<>(newStatus,HttpStatus.CREATED);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('status:edit')")
    public ResponseEntity<Status> updateStatus(@RequestBody Status status){
        Status newStatus = statusService.updateStatus(status);
        return new ResponseEntity<>(newStatus,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('status:delete')")
    public ResponseEntity<?> deleteStatusById(@PathVariable Integer id){
        try{
        statusService.deleteStatusById(id);
        } catch (DataIntegrityViolationException e) {
            throw new StatusException("The Status you trying to delete already in Use");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
