package com.randikalakmal.supplierservice.service;

import com.randikalakmal.supplierservice.dto.ProductDetailsRequest;
import com.randikalakmal.supplierservice.dto.UpdateProductDetailsRequest;
import com.randikalakmal.supplierservice.exception.ProductDetailException;
import com.randikalakmal.supplierservice.model.*;
import com.randikalakmal.supplierservice.repository.ProductDetailsRepository;
import com.randikalakmal.supplierservice.repository.ProductImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProductDetailsService {

    private final ProductDetailsRepository productDetailsRepository;
    private final ProductImageRepository productImageRepository;
    private final RestTemplate restTemplate;
    private final String ADMIN_SERVICE_URL = "http://admin-service/api/admin";


    public List<ProductDetails> getAllProducts() {
        return productDetailsRepository.findAll();
    }

    public ProductDetails getProductByName(String productName) {
        return productDetailsRepository.findByProductName(productName)
                .orElseThrow(() -> new ProductDetailException("Product " + productName + " not found"));
    }

    public ProductDetails getProductById(Integer id){
        return productDetailsRepository.findById(id)
                .orElseThrow(()->new ProductDetailException("Product id "+id+" was not found"));
    }

    public ProductDetails addProductDetail(ProductDetailsRequest productDetailsRequest) {

        String categoryName = productDetailsRequest.getCategoryName().toLowerCase();
        String subCategoryName = productDetailsRequest.getSubCategoryName().toLowerCase();
        String brandName = productDetailsRequest.getBrandName().toLowerCase();

        Category category = null;
        SubCategory subCategory = null;
        Brand brand = null;

        ResponseEntity<Category> categoryResponseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL + "/category/find/name/" + categoryName,
                HttpMethod.GET, null, Category.class);

        if (categoryResponseEntity.getStatusCode() == HttpStatus.OK) {
            category = categoryResponseEntity.getBody();
        }

        if ( !subCategoryName.isEmpty() || !subCategoryName.isBlank()) {
            ResponseEntity<SubCategory> subCategoryResponseEntity = restTemplate.exchange(
                    ADMIN_SERVICE_URL + "/subcategory/find/name/" + subCategoryName,
                    HttpMethod.GET,
                    null,
                    SubCategory.class
            );

            if (subCategoryResponseEntity.getStatusCode() == HttpStatus.OK) {
                subCategory = subCategoryResponseEntity.getBody();
            }
        }

        ResponseEntity<Brand> brandResponseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL + "/brand/find/name/" + brandName,
                HttpMethod.GET, null, Brand.class);

        if (brandResponseEntity.getStatusCode()==HttpStatus.OK){
            brand = brandResponseEntity.getBody();
        }

        //Validation
        addProductDetailValidation(productDetailsRequest,category,subCategory,brand);

        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductName(productDetailsRequest.getProductName().toLowerCase());
        productDetails.setDescription(productDetailsRequest.getDescription().toLowerCase());
        productDetails.setCategory(category);
        productDetails.setSubCategory(subCategory);
        productDetails.setBrand(brand);

        return productDetailsRepository.save(productDetails);

    }


    public ProductDetails updateProductDetails(UpdateProductDetailsRequest updateProductDetailsRequest){

        int productId = updateProductDetailsRequest.getId();
        String productName = updateProductDetailsRequest.getProductName().toLowerCase();
        String productDescription = updateProductDetailsRequest.getDescription();
        String categoryName = updateProductDetailsRequest.getCategoryName().toLowerCase();
        String subCategoryName = updateProductDetailsRequest.getSubCategoryName().toLowerCase();
        String brandName = updateProductDetailsRequest.getBrandName().toLowerCase();

        Category category = null;
        SubCategory subCategory = null;
        Brand brand = null;

        ResponseEntity<Category> categoryResponseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL + "/category/find/name/" + categoryName,
                HttpMethod.GET, null, Category.class);

        if (categoryResponseEntity.getStatusCode() == HttpStatus.OK) {
            category = categoryResponseEntity.getBody();
        }

        if ( !subCategoryName.isEmpty() || !subCategoryName.isBlank()) {
            ResponseEntity<SubCategory> subCategoryResponseEntity = restTemplate.exchange(
                    ADMIN_SERVICE_URL + "/subcategory/find/name/" + subCategoryName,
                    HttpMethod.GET,
                    null,
                    SubCategory.class
            );

            if (subCategoryResponseEntity.getStatusCode() == HttpStatus.OK) {
                subCategory = subCategoryResponseEntity.getBody();
            }
        }

        ResponseEntity<Brand> brandResponseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL + "/brand/find/name/" + brandName,
                HttpMethod.GET, null, Brand.class);

        if (brandResponseEntity.getStatusCode()==HttpStatus.OK){
            brand = brandResponseEntity.getBody();
        }
        updateProductDetailValidation(updateProductDetailsRequest,category,subCategory,brand);

        ProductDetails productDetails = productDetailsRepository.findById(productId)
                .orElseThrow(()->new ProductDetailException("Product id "+productId+" was not found"));

        productDetails.setProductName(productName);
        productDetails.setDescription(productDescription);
        productDetails.setCategory(category);
        productDetails.setSubCategory(subCategory);
        productDetails.setBrand(brand);

        return productDetailsRepository.save(productDetails);
    }


    private void addProductDetailValidation(ProductDetailsRequest productDetailsRequest,Category category,
                                            SubCategory subCategory,Brand brand) {

        String productName = productDetailsRequest.getProductName().toLowerCase();
        String description = productDetailsRequest.getDescription().toLowerCase();
        String categoryName = productDetailsRequest.getCategoryName().toLowerCase();
        String subCategoryName = productDetailsRequest.getSubCategoryName().toLowerCase();
        String brandName = productDetailsRequest.getBrandName().toLowerCase();

        if (productName.isEmpty() || productName.isBlank()) {
            throw new ProductDetailException("Product Name cannot be empty or blank");
        }
//        if (productDetailsRepository.countByProductName(productName) > 0) {
//            throw new ProductDetailException("Product " + productName + " already exist");
//        }
        if (description.isEmpty() || description.isBlank()) {
            throw new ProductDetailException("Product Description cannot be empty or blank");
        }
        if (categoryName.isEmpty() || categoryName.isBlank()) {
            throw new ProductDetailException("Product Category cannot be empty or blank");
        }
        if (brandName.isEmpty() || brandName.isBlank()) {
            throw new ProductDetailException("Product Brand cannot be empty or blank");
        }

        if (productDetailsRepository.findByProductNameAndCategoryAndSubCategoryAndBrand(productName,
                category,subCategory,brand).isPresent()){
            throw new ProductDetailException("This product is already exist, with the same product name, category ," +
                    "subcategory and brand");
        }


    }

    private void updateProductDetailValidation(UpdateProductDetailsRequest updateProductDetailsRequest,Category category,
                                               SubCategory subCategory,Brand brand){
        String productName = updateProductDetailsRequest.getProductName().toLowerCase();
        String description = updateProductDetailsRequest.getDescription().toLowerCase();
        String categoryName = updateProductDetailsRequest.getCategoryName().toLowerCase();
        String brandName = updateProductDetailsRequest.getBrandName().toLowerCase();

        if (productName.isEmpty() || productName.isBlank()) {
            throw new ProductDetailException("Product Name cannot be empty or blank");
        }
        if (description.isEmpty() || description.isBlank()) {
            throw new ProductDetailException("Product Description cannot be empty or blank");
        }
        if (categoryName.isEmpty() || categoryName.isBlank()) {
            throw new ProductDetailException("Product Category cannot be empty or blank");
        }
        if (brandName.isEmpty() || brandName.isBlank()) {
            throw new ProductDetailException("Product Brand cannot be empty or blank");
        }


        if (productDetailsRepository.findByProductNameAndCategoryAndSubCategoryAndBrand(productName,
                category,subCategory,brand).isPresent()){
            throw new ProductDetailException("This product is already exist, with the same product name, category ," +
                    "subcategory and brand");
        }
    }

    public void updateProductImage(Integer productId, MultipartFile mainImageFile, MultipartFile[] extraImageFile){

        ProductDetails productDetails = getProductById(productId);
        String extraImage1=null;
        String extraImage2=null;
        String extraImage3=null;
        String extraImage4=null;

        String mainImagePath = uploadImage(mainImageFile);

        for (int i = 0; i < extraImageFile.length; i++) {
            if (i==0){
                extraImage1 =uploadImage(extraImageFile[i]);
            }if (i==1){
                extraImage2 =uploadImage(extraImageFile[i]);
            }if (i==2){
                extraImage3 =uploadImage(extraImageFile[i]);
            }if (i==3){
                extraImage4 =uploadImage(extraImageFile[i]);
            }

        }

        ProductImage productImage = new ProductImage();
        productImage.setMainImage(mainImagePath);
        productImage.setExtraImage1(extraImage1);
        productImage.setExtraImage2(extraImage2);
        productImage.setExtraImage3(extraImage3);
        productImage.setExtraImage4(extraImage4);
        productImage.setProductDetails(productDetails);

        productImageRepository.save(productImage);
    }

    private String uploadImage(MultipartFile file){

        String getCurrentDateAndTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // Using because same image name can upload
        System.out.println(getCurrentDateAndTime);
        String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileNameFront = fileName.split("\\.")[0];
        String fileExtension=fileName.split("\\.")[1];
        fileNameFront +=getCurrentDateAndTime;
        fileName = fileNameFront+"."+fileExtension;
        System.out.println("File Name"+fileName);
        String uploadDir = "./product-images/";
        Path uploadPath = Paths.get(uploadDir);

        try{
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
        }catch(IOException exception){
            System.out.println("cannot create the product image folder");
            exception.printStackTrace();
        }

        Path filePath = uploadPath.resolve(fileName);
        System.out.println("FILE PATH :" + filePath.toString());

        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath.toString();

    }


}
