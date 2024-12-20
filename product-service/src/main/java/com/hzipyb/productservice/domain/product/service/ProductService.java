package com.hzipyb.productservice.domain.product.service;

import com.hzipyb.productservice.domain.product.client.InventoryClient;
import com.hzipyb.productservice.domain.product.dto.ProductDetailDTO;
import com.hzipyb.productservice.domain.product.entity.Product;
import com.hzipyb.productservice.domain.product.exception.ProductNotFoundException;
import com.hzipyb.productservice.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;

    public ProductDetailDTO getProductById(Long productId){
        return productRepository.getProductById(productId)
                .map(product -> {
                    Integer stockQuantity = inventoryClient.getStockQuantityByProductId(productId);
                    return convertToProductDetailDTO(product, stockQuantity);
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
    }

    public List<ProductDetailDTO> getProductsByCategoryId(Long categoryId, boolean isSoldOutView){
        return productRepository.getProductsByCategoryId(categoryId)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(product -> {
                    Integer stockQuantity = inventoryClient.getStockQuantityByProductId(Long.parseLong(product.getPK().replaceFirst("PRODUCT#", "")));
                    if (!isSoldOutView && (stockQuantity == null || stockQuantity <= 0)) {
                        return null;
                    }
                    return convertToProductDetailDTO(product, stockQuantity);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ProductDetailDTO convertToProductDetailDTO(Product product, Integer stockQuantity) {
        String pkPrefix = "PRODUCT#";
        String skPrefix = "#CATEGORY#";

        return ProductDetailDTO.builder()
                .productId(Long.parseLong(product.getPK().replaceFirst(pkPrefix,"")))
                .categoryId(Long.parseLong(product.getSK().replaceFirst(skPrefix,"")))
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(stockQuantity)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
