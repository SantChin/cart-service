package com.santosh.cart_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AddCartItemRequest(

        @NotBlank(message = "Item name is required")
        String itemName,

        @Min(value = 1, message = "Quantity must be greater than zero")
        Integer quantity,

        @DecimalMin(
                value = "0.01",
                message = "Price must be greater than zero")
        BigDecimal price

) {
}