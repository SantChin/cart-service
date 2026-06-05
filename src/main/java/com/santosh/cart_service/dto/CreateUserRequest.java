package com.santosh.cart_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
		@NotBlank(message = "User name is required") String userName,
		@Email(message = "Invalid email") @NotBlank(message = "Email is required") String email
		) {
}
