package com.ecommerce.service.impl;

import com.ecommerce.dto.OrderCreatedEvent;
import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @Transactional
    public UUID createOrder(UUID userId, BigDecimal total){
        var order = new Order(userId, total);
        orderRepository.save(order);

        var event = new OrderCreatedEvent(
                order.getId(),
                order.getUserId(),
                order.getTotal()
        );
        orderProducer.send(event);
        return order.getId();
    }
}
