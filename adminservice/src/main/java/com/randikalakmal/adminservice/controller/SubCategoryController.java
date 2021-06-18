package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.exception.SubCategoryException;
import com.randikalakmal.adminservice.model.SubCategory;
import com.randikalakmal.adminservice.service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/subcategory")
@AllArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('subcategory:read')")
    public ResponseEntity<List<SubCategory>> getAllSubCategory(){
        List<SubCategory> subCategoryList = subCategoryService.getAllSubCategory();
        return new ResponseEntity<>(subCategoryList, HttpStatus.OK);
    }

    @GetMapping("/find/like/{name}")
    @PreAuthorize("hasAuthority('subcategory:read')")
    public ResponseEntity<List<SubCategory>> getSubCategoryByNameLike(@PathVariable("name") String name){
        List<SubCategory> subCategoryList = subCategoryService.getSubCategoryLikeSubCategoryName(name);
        return new ResponseEntity<>(subCategoryList,HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    @PreAuthorize("hasAuthority('subcategory:read')")
    public ResponseEntity<SubCategory> getSubCategoryByName(@PathVariable("name") String name){
        SubCategory subCategory = subCategoryService.getSubCategoryBySubCategory(name);
        return new ResponseEntity<>(subCategory,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('subcategory:write')")
    public ResponseEntity<SubCategory> addSubCategory(@RequestBody SubCategory subCategory){
        SubCategory addedSubCategory = subCategoryService.addSubCategory(subCategory);
        return new ResponseEntity<>(addedSubCategory,HttpStatus.CREATED);
    }
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('subcategory:edit')")
    public ResponseEntity<SubCategory> updateSubCategory(@RequestBody SubCategory subCategory){
        SubCategory updatedSubCategory = subCategoryService.addSubCategory(subCategory);
        return new ResponseEntity<>(updatedSubCategory,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('subcategory:delete')")
    public ResponseEntity<?> deleteSubCategory(@PathVariable("id") Integer id){
        try {
            subCategoryService.deleteSubCategory(id);
        }catch (DataIntegrityViolationException e){
            throw new SubCategoryException("The Sub Category you trying to delete is in use");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
