package com.hzipyb.inventoryservice.domain.inventory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequestDTO {
    private String productId;
    private Integer changeQuantity;
    private String currentEvent;
}
