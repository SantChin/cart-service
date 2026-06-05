package com.santosh.cart_service.dto;

import java.math.BigDecimal;

public record AddCartItemRequest(
        String itemName,
        Integer quantity,
        BigDecimal price
) {
}