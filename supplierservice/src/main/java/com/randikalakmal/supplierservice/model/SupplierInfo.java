package com.randikalakmal.supplierservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SupplierInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String supplierName;
    private String companyName;
    private String companyRegNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private User user;
}
