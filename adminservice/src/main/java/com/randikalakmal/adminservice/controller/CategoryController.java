package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.exception.CategoryException;
import com.randikalakmal.adminservice.model.Category;
import com.randikalakmal.adminservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('category:read')")
    private ResponseEntity<List<Category>> getAllCategory(){
        List<Category> categoryList = categoryService.getAllCategory();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('category:write')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        Category addedCategory= categoryService.addCategory(category);
        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }
    
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('category:edit')")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        Category updatedCategory = categoryService.updateCategory(category);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    @PreAuthorize("hasAuthority('category:read')")
    public ResponseEntity<Category> getCategoryByName(@PathVariable("name") String name){
        Category category = categoryService.getCategoryByCategory(name);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @GetMapping("/find/like/{name}")
    @PreAuthorize("hasAuthority('category:read')")
    public ResponseEntity<List<Category>> getCategoryByNameLike(@PathVariable("name") String name){
        List<Category> categoryList = categoryService.getCategoryByCategoryNameLike(name);
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('category:delete')")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id){
        try{
            categoryService.deleteCategoryById(id);
        }catch(DataIntegrityViolationException e){
            throw new CategoryException("The Category you trying to delete is in use");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    
}
