package com.hzipyb.payservice.domain.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hzipyb.payservice.config.SqsMessageSender;
import com.hzipyb.payservice.domain.payment.dto.PaymentChangeDTO;
import com.hzipyb.payservice.domain.payment.dto.PaymentRequestDTO;
import com.hzipyb.payservice.domain.payment.entity.Payment;
import com.hzipyb.payservice.domain.payment.entity.PaymentMethod;
import com.hzipyb.payservice.domain.payment.entity.PaymentStatus;
import com.hzipyb.payservice.domain.payment.exception.PaymentNotFoundException;
import com.hzipyb.payservice.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final SqsMessageSender messageSender;

    public Payment getPaymentById(Long paymentId){
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + paymentId));
    }

    public Payment createPayment(PaymentRequestDTO paymentRequestDTO){
        Payment newPayment = new Payment();

        newPayment.setOrderId(paymentRequestDTO.getOrder().getId());
        newPayment.setAmount(paymentRequestDTO.getOrder().getTotalAmount());
        newPayment.setPaymentMethod(PaymentMethod.valueOf(paymentRequestDTO.getPaymentMethod()));
        newPayment.setPaymentStatus(PaymentStatus.COMPLETED);

        newPayment = paymentRepository.save(newPayment);

        messageSender.sendMessage(newPayment.getOrderId().toString());

        return newPayment;
    }

    public Payment updatePaymentById(Long paymentId, PaymentChangeDTO paymentChangeDTO){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + paymentId));

        payment.setPaymentStatus(PaymentStatus.valueOf(paymentChangeDTO.getStatus()));

        return paymentRepository.save(payment);
    }
}
