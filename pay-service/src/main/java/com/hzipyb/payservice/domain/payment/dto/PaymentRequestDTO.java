package com.hzipyb.payservice.domain.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {
    private OrderDTO order;
    private String  paymentMethod;
}
