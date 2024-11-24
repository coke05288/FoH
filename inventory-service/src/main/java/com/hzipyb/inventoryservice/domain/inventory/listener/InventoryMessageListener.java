package com.hzipyb.inventoryservice.domain.inventory.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hzipyb.inventoryservice.config.SqsMessageSender;
import com.hzipyb.inventoryservice.domain.inventory.dto.OrderItemDTO;
import com.hzipyb.inventoryservice.domain.inventory.dto.OrderMessageDTO;
import com.hzipyb.inventoryservice.domain.inventory.service.InventoryService;
import com.hzipyb.inventoryservice.exception.InventorySoldOutException;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryMessageListener {

    private final InventoryService inventoryService;
    private final SqsMessageSender messageSender;
    private final ObjectMapper objectMapper;

    @SqsListener(value = "${cloud.aws.sqs.queue.listener-name}", factory = "defaultSqsListenerContainerFactory")
    public void processOrderMessage(String message){
        try{
            OrderMessageDTO orderMessage = objectMapper.readValue(message, OrderMessageDTO.class);

            List<OrderItemDTO> orderItems = orderMessage.getOrderItems();

            for(OrderItemDTO orderItem : orderItems){
                try{
                    inventoryService.updateInventoryByProductId(
                            orderItem.getProductId(),
                            -orderItem.getQuantity(),
                            "Sold"
                    );
                    System.out.println("Inventory updated successfully for productId: " + orderItem.getProductId());
                }catch (InventorySoldOutException e){
                    System.out.println("Inventory sold out for productId: " + orderItem.getProductId() + ". " + e.getMessage());
                    handleFailure(message, e.getMessage());
                }
            }

            System.out.println("Inventory updated successfully for orderId: " + orderMessage.getId());

            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.registerModule(new JavaTimeModule());
            String toPayMessage = jsonMapper.writeValueAsString(orderMessage);

            messageSender.sendMessage(toPayMessage);

        }catch (Exception e){
            System.out.println("Error processing order message: " + e.getMessage());
        }
    }

    private void handleFailure(String message, String reason) {
        // 보상 트랜잭션 로직 구현
        System.out.println("Handling failure for message: " + message + ", reason: " + reason);
    }
}
