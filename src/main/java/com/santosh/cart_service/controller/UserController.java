package com.santosh.cart_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santosh.cart_service.dto.CreateUserRequest;
import com.santosh.cart_service.dto.UserResponse;
import com.santosh.cart_service.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    
    UserController(UserService userService){
    	this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody
            CreateUserRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                userService.getUser(userId));
    }
}
