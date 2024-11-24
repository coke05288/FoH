package com.hzipyb.orderservice.domain.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hzipyb.orderservice.config.SqsMessageSender;
import com.hzipyb.orderservice.domain.order.client.ProductClient;
import com.hzipyb.orderservice.domain.order.dto.OrderChangeDTO;
import com.hzipyb.orderservice.domain.order.dto.ProductDTO;
import com.hzipyb.orderservice.domain.order.dto.ProductOrderDTO;
import com.hzipyb.orderservice.domain.order.entity.Order;
import com.hzipyb.orderservice.domain.order.entity.OrderItem;
import com.hzipyb.orderservice.domain.order.entity.OrderStatus;
import com.hzipyb.orderservice.domain.order.exception.OrderNotFoundException;
import com.hzipyb.orderservice.domain.order.repository.OrderItemRepository;
import com.hzipyb.orderservice.domain.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final SqsMessageSender sqsMessageSender;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final ProductClient productClient;

    public Order getOrderById(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }

    public Order createOrder(Long userId, List<ProductOrderDTO> productOrders) throws JsonProcessingException {
        Order newOrder = new Order();
        newOrder.setUserId(userId);
        newOrder.setStatus(OrderStatus.PENDING);

        newOrder = orderRepository.save(newOrder);

        List<OrderItem> orderItems = createOrderItems(newOrder, productOrders);
        newOrder.setOrderItems(orderItems);

        newOrder.setTotalAmount(orderItems.stream()
                .mapToInt(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                .sum()
        );

        // null 체크 후 예외 처리
        if (newOrder.getTotalAmount() == 0 || newOrder.getTotalAmount() == null) {
            newOrder.setTotalAmount(0);
        }

        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        String toInventoryMessage = jsonMapper.writeValueAsString(newOrder);
        sqsMessageSender.sendMessage(toInventoryMessage);

        return orderRepository.save(newOrder);
    }

    public Order createOrderDummy(Long userId, String date, List<ProductOrderDTO> productOrders) throws JsonProcessingException {
        Order newOrder = new Order();

        newOrder.setUserId(userId);

        OrderStatus[] statuses = OrderStatus.values();
        Random random = new Random();
        OrderStatus randomStatus = statuses[random.nextInt(statuses.length)];
        newOrder.setStatus(randomStatus);

        newOrder = orderRepository.save(newOrder);

        List<OrderItem> orderItems = createOrderItems(newOrder, productOrders);
        newOrder.setOrderItems(orderItems);

        newOrder.setTotalAmount(orderItems.stream()
                .mapToInt(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                .sum()
        );

        // null 체크 후 예외 처리
        if (newOrder.getTotalAmount() == 0 || newOrder.getTotalAmount() == null) {
            newOrder.setTotalAmount(0);
        }

        // ObjectMapper jsonMapper = new ObjectMapper();
        // jsonMapper.registerModule(new JavaTimeModule());
        // String message = jsonMapper.writeValueAsString(newOrder);
        // sqsMessageSender.sendMessage(message);

        // date = "2024.11.21 00:00:00"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        LocalDateTime formattedDate = LocalDateTime.parse(date, formatter);

        newOrder.setCreatedAt(formattedDate);
        newOrder.setUpdatedAt(formattedDate);

        return orderRepository.saveAndFlush(newOrder);
    }

    public List<OrderItem> createOrderItems(Order order, List<ProductOrderDTO> productOrders){
        return productOrders.stream()
                .map(productOrder -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(productOrder.getProductId());
                    orderItem.setQuantity(productOrder.getQuantity());

                    ProductDTO productDTO = productClient.getProductByProductId(productOrder.getProductId());

                    orderItem.setPrice(productDTO.getPrice() * productOrder.getQuantity());
                    orderItem.setOrder(order);

                    orderItemRepository.save(orderItem);

                    return orderItem;
                })
                .collect(Collectors.toList());
    }

    public Order updateOrderById(Long orderId, OrderChangeDTO orderChangeDTO){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        order.setStatus(OrderStatus.valueOf(orderChangeDTO.getStatus()));

        return orderRepository.save(order);
    }
}
