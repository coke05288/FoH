package com.hzipyb.orderservice.domain.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hzipyb.orderservice.domain.order.dto.OrderChangeDTO;
import com.hzipyb.orderservice.domain.order.dto.OrderRequestDTO;
import com.hzipyb.orderservice.domain.order.entity.Order;
import com.hzipyb.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderRequestDTO orderRequestDTO) throws JsonProcessingException {

        Order order = orderService.createOrder(orderRequestDTO.getUserId(), orderRequestDTO.getProductOrders());

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long orderId){

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrderById(
            @PathVariable Long orderId,
            @RequestBody OrderChangeDTO orderChangeDTO){

        Order order = orderService.updateOrderById(orderId, orderChangeDTO);

        return ResponseEntity.ok(order);
    }
}
