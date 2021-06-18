package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.exception.GenderException;
import com.randikalakmal.adminservice.model.Gender;
import com.randikalakmal.adminservice.service.GenderService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/gender")
@AllArgsConstructor
public class GenderController {

    private final GenderService genderService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('gender:read')")
    public ResponseEntity<List<Gender>> getAllGender(){
        List<Gender> genderList = genderService.getAllGender();
        return new ResponseEntity<>(genderList, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('gender:write')")
    public ResponseEntity<Gender> addGender(@RequestBody Gender gender){
        Gender newGender = genderService.addGender(gender);
        return new ResponseEntity<>(newGender,HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('gender:read')")
    public ResponseEntity<Gender> findGenderById(@PathVariable("id") Integer id){
        Gender gender = genderService.findGenderById(id);
        return new ResponseEntity<>(gender,HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('gender:edit')")
    public ResponseEntity<Gender> updateGender(@RequestBody Gender gender){
        Gender newGender  =genderService.updateGender(gender);
        return new ResponseEntity<>(newGender,HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    @PreAuthorize("hasAuthority('gender:read')")
    public ResponseEntity<Gender> findGenderByGender(@PathVariable("name") String gender){
        Gender genderList = genderService.findGenderByGender(gender);
        return new ResponseEntity<>(genderList,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('gender:delete')")
    public ResponseEntity<?> deleteGenderById(@PathVariable("id") Integer id){
        try{
        genderService.deleteGenderById(id);
        }catch(DataIntegrityViolationException e){
            throw new GenderException("The Gender you trying to delete already in Use");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
