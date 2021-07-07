package com.randikalakmal.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierFeedbackUpdateRequest extends SupplierFeedbackRequest{

    private int id;
}
