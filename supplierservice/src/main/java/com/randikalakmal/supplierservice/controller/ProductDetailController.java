package com.randikalakmal.supplierservice.controller;

import com.randikalakmal.supplierservice.dto.ProductDetailsRequest;
import com.randikalakmal.supplierservice.dto.UpdateProductDetailsRequest;
import com.randikalakmal.supplierservice.model.ProductDetails;
import com.randikalakmal.supplierservice.service.ProductDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier/product")
@AllArgsConstructor
public class ProductDetailController {

    private final ProductDetailsService productDetailsService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDetails>> getAllProductDetails(){
        List<ProductDetails> productDetailsList = productDetailsService.getAllProducts();
        return new ResponseEntity<>(productDetailsList, HttpStatus.OK);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<ProductDetails> getProductDetailsByProductName(@PathVariable("name") String productName){
        ProductDetails productDetails =productDetailsService.getProductByName(productName);
        return new ResponseEntity<>(productDetails,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDetails> addProductDetails(@RequestBody ProductDetailsRequest productDetailsRequest){
        ProductDetails productDetails = productDetailsService.addProductDetail(productDetailsRequest);
        return new ResponseEntity<>(productDetails,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDetails> updateProductDetails(
            @RequestBody UpdateProductDetailsRequest updateProductDetailsRequest){
        ProductDetails productDetails = productDetailsService.updateProductDetails(updateProductDetailsRequest);
        return new ResponseEntity<>(productDetails,HttpStatus.OK);
    }
}
