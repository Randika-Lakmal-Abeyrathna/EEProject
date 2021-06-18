package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.model.Brand;
import com.randikalakmal.adminservice.service.BrandImageService;
import com.randikalakmal.adminservice.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/brand")
@AllArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final BrandImageService brandImageService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('brand:read')")
    public ResponseEntity<List<Brand>> getAllBrand(){
        List<Brand> brandList = brandService.getAllBrand();
        return new ResponseEntity<>(brandList, HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    @PreAuthorize("hasAuthority('brand:read')")
    public ResponseEntity<Brand> getBrandByBrandName(@PathVariable("name")String brandName){
        Brand brand = brandService.getBrandByBrandName(brandName);
        return new ResponseEntity<>(brand,HttpStatus.OK);
    }

    @GetMapping("/find/like/{name}")
    @PreAuthorize("hasAuthority('brand:read')")
    public ResponseEntity<List<Brand>> getBrandByBrandNameLike(@PathVariable("name")String brandName){
        List<Brand> brandList = brandService.getBrandByBrandNameLike(brandName);
        return new ResponseEntity<>(brandList,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('brand:write')")
    public ResponseEntity<Brand> addBrand(@RequestBody Brand brand){
        Brand addedBrand = brandService.addBrand(brand);
        return new ResponseEntity<>(addedBrand,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('brand:edit')")
    public ResponseEntity<Brand> updateBrand(@RequestBody Brand brand){
        Brand updatedBrand = brandService.updateBrand(brand);
        return new ResponseEntity<>(updatedBrand,HttpStatus.OK);
    }

    @PutMapping("/update/image")
    @PreAuthorize("hasAnyAuthority('brand:write','brand:edit')")
    public ResponseEntity<?> updateImage(@RequestParam("brand") String brandName, @RequestParam("image")MultipartFile imageFile){
        brandImageService.updateBrandImage(brandName,imageFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
