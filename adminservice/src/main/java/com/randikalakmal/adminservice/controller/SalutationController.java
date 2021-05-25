package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.exception.SalutationException;
import com.randikalakmal.adminservice.model.Salutation;
import com.randikalakmal.adminservice.service.SalutationService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/salutation")
@AllArgsConstructor
public class SalutationController {

    private final SalutationService salutationService;

    @GetMapping("/all")
    public ResponseEntity<List<Salutation>> getAllSalutations() {
        List<Salutation> salutationList = salutationService.findAllSalutations();
        return new ResponseEntity<>(salutationList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Salutation> getSalutationById(@PathVariable Integer id) {
        Salutation salutation = salutationService.findById(id);
        return new ResponseEntity<>(salutation, HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<Salutation> getSalutationBySalutation(@PathVariable String name){
        Salutation salutationList = salutationService.findSalutationBySalutation(name);
        return new ResponseEntity<>(salutationList,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Salutation> addSalutation(@RequestBody Salutation salutation) {
        Salutation newSalutation = salutationService.addSalutation(salutation);
        return new ResponseEntity<>(newSalutation, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Salutation> updateSalutation(@RequestBody Salutation salutation) {
        Salutation newSalutation = salutationService.updateSalutation(salutation);
        return new ResponseEntity<>(newSalutation, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSalutationById(@PathVariable Integer id) {
        try {
            salutationService.deleteSalutationById(id);
        } catch (DataIntegrityViolationException e) {
            throw new SalutationException("The Salutation you trying to delete already in Use");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
