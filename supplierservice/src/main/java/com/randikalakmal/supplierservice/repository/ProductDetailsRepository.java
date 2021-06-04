package com.randikalakmal.supplierservice.repository;

import com.randikalakmal.supplierservice.model.Brand;
import com.randikalakmal.supplierservice.model.Category;
import com.randikalakmal.supplierservice.model.ProductDetails;
import com.randikalakmal.supplierservice.model.SubCategory;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails,Integer> {

    List<ProductDetails> findByProductNameIgnoreCaseStartsWith(String productName);

    Optional<ProductDetails> findByProductNameAndCategoryAndSubCategoryAndBrand(String productName,
                                                                                Category category,
                                                                                SubCategory subCategory,
                                                                                Brand brand);
    @NotNull
    Optional<ProductDetails> findById(@NotNull Integer id);

    Optional<ProductDetails> findByProductName(String productName);

    Integer countByProductName(String productName);
}
