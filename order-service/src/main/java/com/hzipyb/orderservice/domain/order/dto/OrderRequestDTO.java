package com.hzipyb.orderservice.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
    private String userId;
    private String productId;
}
