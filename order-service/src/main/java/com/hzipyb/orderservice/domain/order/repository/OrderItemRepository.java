package com.hzipyb.orderservice.domain.order.repository;

import com.hzipyb.orderservice.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
