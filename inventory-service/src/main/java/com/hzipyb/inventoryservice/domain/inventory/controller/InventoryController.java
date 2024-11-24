package com.hzipyb.inventoryservice.domain.inventory.controller;

import com.hzipyb.inventoryservice.domain.inventory.dto.InventoryDTO;
import com.hzipyb.inventoryservice.domain.inventory.dto.InventoryRequestDTO;
import com.hzipyb.inventoryservice.domain.inventory.service.InventoryService;
import com.hzipyb.inventoryservice.exception.InventorySoldOutException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/inventory")
@RequiredArgsConstructor
@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductId(
            @PathVariable Long productId){

        InventoryDTO inventoryDTO = inventoryService.getInventoryById(productId);

        return ResponseEntity.ok(inventoryDTO);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Object> updateInventoryByProductId(
            @PathVariable Long productId,
            @RequestBody InventoryRequestDTO inventoryRequestDTO){
        try {
            InventoryDTO inventoryDTO = inventoryService.updateInventoryByProductId(productId, inventoryRequestDTO.getChangeQuantity(), inventoryRequestDTO.getCurrentEvent());
            return ResponseEntity.ok(inventoryDTO);
        } catch (InventorySoldOutException e){
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", "Unknown error occurred"));
        }
    }
}
