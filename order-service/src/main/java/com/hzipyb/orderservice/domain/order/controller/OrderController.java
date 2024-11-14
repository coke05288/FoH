package com.hzipyb.orderservice.domain.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> orderProductsById(
            @PathVariable Long orderId){

        Order order = orderService.getOrder(orderId);

        return ResponseEntity.ok(order);
    }

    @PostMapping("/create")
    public ResponseEntity<Order> orderProductsById(
            @RequestBody OrderRequestDTO orderRequestDTO) throws JsonProcessingException {

        Order order = orderService.createOrder(orderRequestDTO.getUserId(), orderRequestDTO.getProductOrders());

        return ResponseEntity.ok(order);
    }

}
