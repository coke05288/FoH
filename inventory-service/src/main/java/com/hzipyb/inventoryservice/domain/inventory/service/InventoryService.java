package com.hzipyb.inventoryservice.domain.inventory.service;

import com.hzipyb.inventoryservice.domain.inventory.dto.InventoryDTO;
import com.hzipyb.inventoryservice.domain.inventory.entity.Inventory;
import com.hzipyb.inventoryservice.domain.inventory.repository.InventoryRepository;
import com.hzipyb.inventoryservice.exception.InventoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryDTO getInventoryById(Long productId){
        return inventoryRepository.getInventoryById(productId)
                .map(this::convertToInventoryDTO)
                .orElseThrow(() -> new InventoryNotFoundException("Item not found with ID: " + productId));
    }

    public InventoryDTO updateInventoryByProductId(Long productId, Integer changeQuantity, String currentEvent){
        Inventory inventory = inventoryRepository.getInventoryById(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Item not found for productId"));

        if(inventory.getStockQuantity() <= 0){
            throw new RuntimeException("품절");
        }

        inventory.setStockQuantity(inventory.getStockQuantity() + changeQuantity);
        inventory.setChangeQuantity(changeQuantity);
        inventory.setCurrentEvent(currentEvent);
        String currentDateTime = LocalDateTime.now(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);
        inventory.setUpdatedAt(currentDateTime);

        return inventoryRepository.updateInventory(inventory)
                .map(this::convertToInventoryDTO)
                .orElseThrow();
    }


    private InventoryDTO convertToInventoryDTO(Inventory inventory){
        String pkPrefix = "INVENTORY#";
        String skPrefix = "PRODUCT#";

        return InventoryDTO.builder()
                .inventoryId(Long.parseLong(inventory.getPK().replaceFirst(pkPrefix, "")))
                .productId(Long.parseLong(inventory.getSK().replaceFirst(skPrefix, "")))
                .name(inventory.getName())
                .stockQuantity(inventory.getStockQuantity())
                .changeQuantity(inventory.getChangeQuantity())
                .currentEvent(inventory.getCurrentEvent())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }
}
