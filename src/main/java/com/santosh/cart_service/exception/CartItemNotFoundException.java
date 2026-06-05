package com.santosh.cart_service.exception;

public class CartItemNotFoundException extends RuntimeException {

	public CartItemNotFoundException(Long itemId) {
		super("Cart item not found : " + itemId);
	}
}