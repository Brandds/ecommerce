package com.ecommerce.model;

import com.ecommerce.enuns.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Getter
    @Id
    private UUID id;

    @Getter
    private UUID userId;

    @Getter
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(){}

    public Order(UUID userId, BigDecimal total){
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.total = total;
        this.status =  OrderStatus.CREATED;

    }

}
