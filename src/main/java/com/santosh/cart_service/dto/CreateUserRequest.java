package com.santosh.cart_service.dto;

public record CreateUserRequest(
        String userName,
        String email
) {
}
