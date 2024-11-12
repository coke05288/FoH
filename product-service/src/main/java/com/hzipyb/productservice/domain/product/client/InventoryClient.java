package com.hzipyb.productservice.domain.product.client;

import com.hzipyb.productservice.domain.product.dto.InventoryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryClient {

    private final RestTemplate restTemplate;
    private final String inventoryServiceUrl;

    public InventoryClient(RestTemplate restTemplate,
                           @Value("${inventory.service.url}") String inventoryServiceUrl) {
        this.restTemplate = restTemplate;
        this.inventoryServiceUrl = inventoryServiceUrl;
    }

    public Integer getStockQuantityByProductId(String productId) {
        String url = inventoryServiceUrl + "/product/" + productId.replaceFirst("PRODUCT#PROD", "");
        InventoryDTO inventoryDTO = restTemplate.getForObject(url, InventoryDTO.class);

        return inventoryDTO != null ? inventoryDTO.getStockQuantity() : 0;
    }
}
