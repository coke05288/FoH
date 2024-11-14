package com.hzipyb.orderservice.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private Long userId;
    private List<ProductOrderDTO> productOrders;
}
