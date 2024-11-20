package com.hzipyb.payservice.domain.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer price;
}
