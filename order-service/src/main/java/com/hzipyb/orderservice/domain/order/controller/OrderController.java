package com.hzipyb.orderservice.domain.order.controller;

import com.hzipyb.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class OrderController {

    private final OrderService orderService;

//    @PostMapping("/order/{id}")
//    public void orderProductById(@PathVariable Long id) throws JsonProcessingException {
//        productService.orderProductById(id);
//
//        if(isSuccess){
//            return ResponseEntity.ok("Order Success for product: " + id);
//        }else{
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order failed or insufficient stock.");
//        }
//    }

}
