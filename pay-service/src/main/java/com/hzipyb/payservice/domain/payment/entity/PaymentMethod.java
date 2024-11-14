package com.hzipyb.payservice.domain.payment.entity;

public enum PaymentMethod {
    CREDIT_CARD,      // 신용카드
    BANK_TRANSFER,    // 은행 이체
    MOBILE            // 모바일 결제 (예: 삼성페이, 애플페이)
}