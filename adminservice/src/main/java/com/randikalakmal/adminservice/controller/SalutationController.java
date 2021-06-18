package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.exception.SalutationException;
import com.randikalakmal.adminservice.model.Salutation;
import com.randikalakmal.adminservice.service.SalutationService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/salutation")
@AllArgsConstructor
public class SalutationController {

    private final SalutationService salutationService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('salutation:read')")
    public ResponseEntity<List<Salutation>> getAllSalutations() {
        List<Salutation> salutationList = salutationService.findAllSalutations();
        return new ResponseEntity<>(salutationList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('salutation:read')")
    public ResponseEntity<Salutation> getSalutationById(@PathVariable("id") Integer id) {
        Salutation salutation = salutationService.findById(id);
        return new ResponseEntity<>(salutation, HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    @PreAuthorize("hasAuthority('salutation:read')")
    public ResponseEntity<Salutation> getSalutationBySalutation(@PathVariable("name") String name){
        Salutation salutationList = salutationService.findSalutationBySalutation(name);
        return new ResponseEntity<>(salutationList,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('salutation:write')")
    public ResponseEntity<Salutation> addSalutation(@RequestBody Salutation salutation) {
        Salutation newSalutation = salutationService.addSalutation(salutation);
        return new ResponseEntity<>(newSalutation, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('salutation:edit')")
    public ResponseEntity<Salutation> updateSalutation(@RequestBody Salutation salutation) {
        Salutation newSalutation = salutationService.updateSalutation(salutation);
        return new ResponseEntity<>(newSalutation, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('salutation:delete')")
    public ResponseEntity<?> deleteSalutationById(@PathVariable("id") Integer id) {
        try {
            salutationService.deleteSalutationById(id);
        } catch (DataIntegrityViolationException e) {
            throw new SalutationException("The Salutation you trying to delete already in Use");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
