package com.ecommerce.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(UUID orderId, UUID userID, BigDecimal total) {
}
