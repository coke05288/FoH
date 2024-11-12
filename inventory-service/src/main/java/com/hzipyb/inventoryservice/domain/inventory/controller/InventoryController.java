package com.hzipyb.inventoryservice.domain.inventory.controller;

import com.hzipyb.inventoryservice.domain.inventory.dto.InventoryDTO;
import com.hzipyb.inventoryservice.domain.inventory.dto.InventoryRequestDTO;
import com.hzipyb.inventoryservice.domain.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/inventory")
@RequiredArgsConstructor
@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductId(
            @PathVariable String productId){

        InventoryDTO inventoryDTO = inventoryService.getInventoryById(productId);

        return ResponseEntity.ok(inventoryDTO);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<InventoryDTO> updateInventoryByProductId(
            @PathVariable String productId,
            @RequestBody InventoryRequestDTO inventoryRequestDTO){
        try {
            InventoryDTO inventoryDTO = inventoryService.updateInventoryByProductId(productId, inventoryRequestDTO.getChangeQuantity(), inventoryRequestDTO.getCurrentEvent());
            return ResponseEntity.ok(inventoryDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
