package com.hzipyb.payservice.domain.payment.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzipyb.payservice.config.SqsMessageSender;
import com.hzipyb.payservice.domain.payment.dto.OrderDTO;
import com.hzipyb.payservice.domain.payment.dto.PaymentRequestDTO;
import com.hzipyb.payservice.domain.payment.service.PaymentService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMessageListener {

    private final PaymentService paymentService;
    private final SqsMessageSender messageSender;
    private final ObjectMapper objectMapper;

    @SqsListener(value = "${cloud.aws.sqs.queue.listener-name}", factory = "defaultSqsListenerContainerFactory")
    public void processInventoryMessage(String message){
        try{
            OrderDTO inventoryOrderMessage = objectMapper.readValue(message, OrderDTO.class);

            PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();

            paymentRequestDTO.setOrder(inventoryOrderMessage);
            paymentRequestDTO.setPaymentMethod("CREDIT_CARD");

            paymentService.createPayment(paymentRequestDTO);

            System.out.println("Inventory updated successfully for orderId: " + inventoryOrderMessage.getId());

        }catch (Exception e){
            System.out.println("Error processing inventory message: " + e.getMessage());
        }
    }
}
