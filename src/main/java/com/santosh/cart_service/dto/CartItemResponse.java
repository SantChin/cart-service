package com.santosh.cart_service.dto;

import java.math.BigDecimal;

public record CartItemResponse(
        Long cartItemId,
        String itemName,
        Integer quantity,
        BigDecimal price
) {
}
