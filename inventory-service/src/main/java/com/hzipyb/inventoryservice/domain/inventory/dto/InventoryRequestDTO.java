package com.hzipyb.inventoryservice.domain.inventory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequestDTO {
    private Long productId;
    private Integer changeQuantity;
    private String currentEvent;
}
