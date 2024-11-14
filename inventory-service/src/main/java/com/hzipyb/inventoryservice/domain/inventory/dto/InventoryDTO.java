package com.hzipyb.inventoryservice.domain.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long inventoryId;
    private Long productId;

    private String name;
    private Integer stockQuantity;
    private Integer changeQuantity;
    private String currentEvent;
    private String updatedAt;
}
