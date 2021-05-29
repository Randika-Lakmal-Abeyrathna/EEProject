package com.randikalakmal.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String email;
    private String addressNo;
    private String addressStreet1;
    private String addressStreet2;
    private Integer contactNumber1;
    private Integer contactNumber2;
    private String password;
    private String city;
    private String status;
    private String userType;
}
