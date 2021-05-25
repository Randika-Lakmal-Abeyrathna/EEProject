package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.model.Brand;
import com.randikalakmal.adminservice.service.BrandImageService;
import com.randikalakmal.adminservice.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Brand>> getAllBrand(){
        List<Brand> brandList = brandService.getAllBrand();
        return new ResponseEntity<>(brandList, HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<Brand> getBrandByBrandName(@PathVariable("name")String brandName){
        Brand brand = brandService.getBrandByBrandName(brandName);
        return new ResponseEntity<>(brand,HttpStatus.OK);
    }

    @GetMapping("/find/like/{name}")
    public ResponseEntity<List<Brand>> getBrandByBrandNameLike(@PathVariable("name")String brandName){
        List<Brand> brandList = brandService.getBrandByBrandNameLike(brandName);
        return new ResponseEntity<>(brandList,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Brand> addBrand(@RequestBody Brand brand){
        Brand addedBrand = brandService.addBrand(brand);
        return new ResponseEntity<>(addedBrand,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Brand> updateBrand(@RequestBody Brand brand){
        Brand updatedBrand = brandService.updateBrand(brand);
        return new ResponseEntity<>(updatedBrand,HttpStatus.OK);
    }

    @PutMapping("/update/image")
    public ResponseEntity<?> updateImage(@RequestParam("brand") String brandName, @RequestParam("image")MultipartFile imageFile){
        brandImageService.updateBrandImage(brandName,imageFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
