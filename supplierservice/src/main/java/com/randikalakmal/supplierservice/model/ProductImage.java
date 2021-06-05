package com.randikalakmal.supplierservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String mainImage;
    private String extraImage1;
    private String extraImage2;
    private String extraImage3;
    private String extraImage4;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_details_id",referencedColumnName = "id")
    private ProductDetails productDetails;
}
