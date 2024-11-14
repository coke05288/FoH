package com.hzipyb.orderservice.domain.order.entity;

public enum OrderStatus {
    PENDING,    // 주문 대기 중
    CONFIRMED,  // 주문 확인됨
    SHIPPED,    // 배송 중
    DELIVERED,  // 배송 완료
    CANCELLED   // 주문 취소됨
}