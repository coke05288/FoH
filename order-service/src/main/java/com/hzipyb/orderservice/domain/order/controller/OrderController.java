package com.hzipyb.orderservice.domain.order.controller;

import com.hzipyb.orderservice.domain.order.dto.OrderRequestDTO;
import com.hzipyb.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Configuration
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public void orderProductsById(
            @RequestBody OrderRequestDTO orderRequestDTO){


//        productService.orderProductById(id);
//
//        if(isSuccess){
//            return ResponseEntity.ok("Order Success for product: " + id);
//        }else{
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order failed or insufficient stock.");
//        }
    }

}
