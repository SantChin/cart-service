package com.santosh.cart_service.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santosh.cart_service.dto.AddCartItemRequest;
import com.santosh.cart_service.dto.CartItemResponse;
import com.santosh.cart_service.dto.CartResponse;
import com.santosh.cart_service.entity.Cart;
import com.santosh.cart_service.entity.CartItem;
import com.santosh.cart_service.entity.User;
import com.santosh.cart_service.exception.CartItemNotFoundException;
import com.santosh.cart_service.exception.CartNotFoundException;
import com.santosh.cart_service.exception.UserNotFoundException;
import com.santosh.cart_service.repository.CartItemRepository;
import com.santosh.cart_service.repository.CartRepository;
import com.santosh.cart_service.repository.UserRepository;
import com.santosh.cart_service.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final CartItemRepository cartItemRepository;

	CartServiceImpl(CartRepository cartRepository, UserRepository userRepository,
			CartItemRepository cartItemRepository) {
		this.cartItemRepository = cartItemRepository;
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
	}

	@Override
	public CartResponse createCart(Long userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

		if (user.getCart() != null) {
			return mapToResponse(user.getCart());
		}

		Cart cart = new Cart();
		cart.setUser(user);

		Cart savedCart = cartRepository.save(cart);

		user.setCart(savedCart);

		return mapToResponse(savedCart);
	}

	@Override
	public CartResponse addItem(Long cartId, AddCartItemRequest request) {

		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));

		CartItem item = new CartItem();
		item.setItemName(request.itemName());
		item.setQuantity(request.quantity());
		item.setPrice(request.price());
		cart.addItem(item);
		Cart savedCart = cartRepository.save(cart);

		return mapToResponse(savedCart);
	}

	@Override
	@Transactional(readOnly = true)
	public CartResponse getCart(Long cartId) {

		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));

		return mapToResponse(cart);
	}

	@Override
	public void removeItem(Long itemId) {

		CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new CartItemNotFoundException(itemId));

		cartItemRepository.delete(item);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateTotal(Long cartId) {

		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));

		return cart.getItemList().stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private CartResponse mapToResponse(Cart cart) {

		List<CartItemResponse> items = cart.getItemList().stream()
				.map(item -> new CartItemResponse(item.getCartItemId(), item.getItemName(), item.getQuantity(),
						item.getPrice()))
				.toList();

		return new CartResponse(cart.getCartId(), cart.getUser().getUserId(), items, calculateCartTotal(cart));
	}

	private BigDecimal calculateCartTotal(Cart cart) {

		return cart.getItemList().stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
