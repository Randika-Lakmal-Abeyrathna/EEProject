package com.randikalakmal.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsRequest {

    private String productName;
    private String description;
    private String categoryName;
    private String subCategoryName;
    private String brandName;

}
