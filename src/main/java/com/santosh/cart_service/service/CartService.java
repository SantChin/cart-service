package com.santosh.cart_service.service;

import java.math.BigDecimal;

import com.santosh.cart_service.dto.AddCartItemRequest;
import com.santosh.cart_service.dto.CartResponse;

public interface CartService {

    CartResponse createCart(
            Long userId);

    CartResponse addItem(
            Long cartId,
            AddCartItemRequest request);

    CartResponse getCart(
            Long cartId);

    void removeItem(
            Long itemId);

    BigDecimal calculateTotal(
            Long cartId);
}
