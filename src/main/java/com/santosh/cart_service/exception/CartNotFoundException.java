package com.santosh.cart_service.exception;

public class CartNotFoundException extends RuntimeException {

	public CartNotFoundException(Long cartId) {
		super("Cart not found : " + cartId);
	}
}