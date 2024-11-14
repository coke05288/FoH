package com.hzipyb.productservice.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    private Long productId;
    private Long categoryId;
    private String name;
    private String description;
    private Integer price;
    private Integer stockQuantity;
    private String createdAt;
    private String updatedAt;
}
