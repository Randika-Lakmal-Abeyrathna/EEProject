package com.randikalakmal.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {

    private double qty;
    private boolean availability;
    private double stockPrice;
    private double marketPrice;
    private double discount;
    private Date expireDate;
    private int supplierId;
    private int productId;
    private int statusId;

}
