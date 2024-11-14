package com.hzipyb.payservice.domain.payment.controller;

import com.hzipyb.payservice.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
}
