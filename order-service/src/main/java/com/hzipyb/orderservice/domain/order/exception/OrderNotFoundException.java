package com.hzipyb.orderservice.domain.order.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message){
        super(message);
    }
}
