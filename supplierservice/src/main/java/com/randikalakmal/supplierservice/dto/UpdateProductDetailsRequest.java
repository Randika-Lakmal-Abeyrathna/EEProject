package com.randikalakmal.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDetailsRequest {

    @NotNull
    private int id;
    private String productName;
    private String description;
    private String categoryName;
    private String subCategoryName;
    private String brandName;
}
