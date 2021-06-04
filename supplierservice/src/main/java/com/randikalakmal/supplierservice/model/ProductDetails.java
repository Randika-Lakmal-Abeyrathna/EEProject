package com.randikalakmal.supplierservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.reactivestreams.Subscription;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String productName;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_categoryid",referencedColumnName = "categoryid")
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_category_idsub_Category",referencedColumnName = "idsubCategory")
    private SubCategory subCategory;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id",referencedColumnName = "id")
    private Brand brand;

}
