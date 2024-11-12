package com.hzipyb.productservice.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private String inventoryId;
    private String productId;

    private String name;
    private Integer stockQuantity;
    private Integer changeQuantity;
    private String currentEvent;
    private String updatedAt;
}
