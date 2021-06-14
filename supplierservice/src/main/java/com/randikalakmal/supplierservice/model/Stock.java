package com.randikalakmal.supplierservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockid;
    private double qty;
    private boolean availability;
    private double stockPrice;
    private double marketPrice;
    private Date expireDate;
    private double discount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_info_id",referencedColumnName = "id")
    private SupplierInfo supplierInfo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_details_id",referencedColumnName = "id")
    private ProductDetails productDetails;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_idstatus",referencedColumnName = "idstatus")
    private Status status;

}
