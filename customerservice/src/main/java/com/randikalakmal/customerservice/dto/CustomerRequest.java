package com.randikalakmal.customerservice.dto;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest extends UserRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String nicNumber;
    private Date dateOfBirth;
    private String gender;
    private String salutation;

}
