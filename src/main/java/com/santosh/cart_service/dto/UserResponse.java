package com.santosh.cart_service.dto;

public record UserResponse(
        Long userId,
        String userName,
        String email
) {
}