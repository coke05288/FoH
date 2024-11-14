package com.hzipyb.payservice.domain.payment.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long userId;
    private String status;
    private Integer totalAmount;
}
