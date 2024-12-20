package com.hzipyb.payservice.domain.payment.entity;

public enum PaymentStatus {
    PENDING,      // 결제 대기 중
    COMPLETED,    // 결제 완료
    FAILED,       // 결제 실패
    CANCELED,     // 결제 취소
    REFUNDED,     // 결제 환불 완료
}