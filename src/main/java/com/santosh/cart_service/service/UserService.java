package com.santosh.cart_service.service;

import com.santosh.cart_service.dto.CreateUserRequest;
import com.santosh.cart_service.dto.UserResponse;

public interface UserService {

    UserResponse createUser(
            CreateUserRequest request);

    UserResponse getUser(
            Long userId);
}