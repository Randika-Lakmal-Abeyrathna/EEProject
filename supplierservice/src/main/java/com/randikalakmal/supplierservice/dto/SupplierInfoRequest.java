package com.randikalakmal.supplierservice.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierInfoRequest extends UserRequest{

    private String supplierName;
    private String companyName;
    private String companyRegNumber;

}
