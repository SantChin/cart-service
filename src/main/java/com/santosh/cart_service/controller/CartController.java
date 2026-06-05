package com.santosh.cart_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santosh.cart_service.dto.AddCartItemRequest;
import com.santosh.cart_service.dto.CartResponse;
import com.santosh.cart_service.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;
    
    CartController(CartService cartService){
    	this.cartService = cartService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CartResponse> createCart(
            @PathVariable Long userId) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartService.createCart(userId));
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartResponse> addItem(

            @PathVariable Long cartId,

            @Valid
            @RequestBody
            AddCartItemRequest request) {

        return ResponseEntity.ok(
                cartService.addItem(
                        cartId,
                        request));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCart(
            @PathVariable Long cartId) {

        return ResponseEntity.ok(
                cartService.getCart(cartId));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long itemId) {

        cartService.removeItem(itemId);

        return ResponseEntity.noContent()
                .build();
    }
}
