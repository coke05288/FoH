package com.hzipyb.orderservice.domain.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping
    public String checkHealth(){
        return "OK";
    }
}
