package com.hzipyb.payservice.domain.payment.controller;

import com.hzipyb.payservice.domain.payment.dto.PaymentChangeDTO;
import com.hzipyb.payservice.domain.payment.dto.PaymentRequestDTO;
import com.hzipyb.payservice.domain.payment.entity.Payment;
import com.hzipyb.payservice.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(
            @RequestBody PaymentRequestDTO paymentRequestDTO){

        Payment payment = paymentService.createPayment(paymentRequestDTO);

        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(
            @PathVariable Long paymentId){

        Payment payment = paymentService.getPaymentById(paymentId);

        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePaymentById(
            @PathVariable Long paymentId,
            @RequestBody PaymentChangeDTO paymentChangeDTO){

        Payment payment = paymentService.updatePaymentById(paymentId, paymentChangeDTO);

        return ResponseEntity.ok(payment);
    }
}
