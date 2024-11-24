package com.hzipyb.orderservice.domain.order.listener;

import com.hzipyb.orderservice.domain.order.dto.OrderChangeDTO;
import com.hzipyb.orderservice.domain.order.service.OrderService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMessageListener {

    private final OrderService orderService;

    @SqsListener(value = "${cloud.aws.sqs.queue.listener-name}", factory = "defaultSqsListenerContainerFactory")
    public void processPayMessage(String message){
        try{
            Long orderId = Long.parseLong(message);
            OrderChangeDTO orderChangeDTO = new OrderChangeDTO();

            orderChangeDTO.setStatus("CONFIRMED");

            orderService.updateOrderById(orderId, orderChangeDTO);

        }catch (Exception e){
            System.out.println("Error processing pay message: " + e.getMessage());
        }
    }

    private void handleFailure(String message, String reason) {
        // 보상 트랜잭션 로직 구현
        System.out.println("Handling failure for message: " + message + ", reason: " + reason);
    }
}
