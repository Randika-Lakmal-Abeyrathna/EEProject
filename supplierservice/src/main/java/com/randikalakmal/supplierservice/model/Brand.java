package com.randikalakmal.supplierservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brandname;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_image_id",referencedColumnName = "id")
    private BrandImage brandImage;

}
