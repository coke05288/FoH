package com.hzipyb.orderservice.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrderDTO {
    private Long productId;
    private Integer quantity;
}
