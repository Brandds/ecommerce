package com.ecommerce.request;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(UUID userId, BigDecimal total) {
}
