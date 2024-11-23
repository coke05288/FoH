package com.hzipyb.orderservice.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDummyDTO {
    private Long userId;
    private String date;
    private List<ProductOrderDTO> productOrders;
}
