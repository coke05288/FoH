package com.hzipyb.orderservice.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Order {
    @Id
    private Long Id;
}
