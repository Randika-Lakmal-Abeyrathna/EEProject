package com.randikalakmal.adminservice.dto;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoRequest extends UserRequest{

    private String firstName;
    private String middleName;
    private String lastName;
}
