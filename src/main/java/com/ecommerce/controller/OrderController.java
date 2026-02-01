package com.ecommerce.controller;

import com.ecommerce.request.CreateOrderRequest;
import com.ecommerce.service.impl.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(
            @RequestBody CreateOrderRequest request
    ){
        var orderId = service.createOrder(
                request.userId(),
                request.total()
        );

        return ResponseEntity.ok(Map.of("orderId", orderId));
    }
}
