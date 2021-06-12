package com.randikalakmal.supplierservice.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockUpdateRequest extends StockRequest{
    private int id;

}
