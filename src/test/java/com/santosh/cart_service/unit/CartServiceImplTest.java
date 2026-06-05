package com.santosh.cart_service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.santosh.cart_service.dto.AddCartItemRequest;
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
import com.santosh.cart_service.service.impl.CartServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

	@Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartServiceImpl cartService;
    
    
    @Test
    void shouldCreateCart() {

        User user = new User();
        user.setUserId(1L);

        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setUser(user);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(cartRepository.save(any(Cart.class)))
                .thenReturn(cart);

        CartResponse response =
                cartService.createCart(1L);

        assertNotNull(response);

        assertEquals(1L,
                response.userId());
    }
    
    
    @Test
    void shouldThrowUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> cartService.createCart(1L));
    }
    
    
    @Test
    void shouldAddItem() {

        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setItemList(new ArrayList<>());

        User user = new User();
        user.setUserId(1L);
        

        cart.setUser(user);

        AddCartItemRequest request =
                new AddCartItemRequest(
                        "Laptop",
                        2,
                        BigDecimal.valueOf(50000));

        when(cartRepository.findById(1L))
                .thenReturn(Optional.of(cart));

        when(cartRepository.save(any(Cart.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        CartResponse response =
                cartService.addItem(1L,
                        request);

        assertEquals(
                1,
                response.items().size());
    }
    
    @Test
    void shouldThrowCartNotFound() {

        when(cartRepository.findById(1L))
                .thenReturn(Optional.empty());

        AddCartItemRequest request =
                new AddCartItemRequest(
                        "Laptop",
                        1,
                        BigDecimal.TEN);

        assertThrows(
                CartNotFoundException.class,
                () -> cartService.addItem(
                        1L,
                        request));
    }
    
    @Test
    void shouldCalculateTotal() {
        
        CartItem item1 = new CartItem();
        item1.setPrice(BigDecimal.valueOf(100));
        item1.setQuantity(2);
        
        CartItem item2 = new CartItem();
        item2.setPrice(BigDecimal.valueOf(50));
        item2.setQuantity(3);

        Cart cart = new Cart();
        cart.setItemList(
                List.of(item1, item2));

        when(cartRepository.findById(1L))
                .thenReturn(Optional.of(cart));

        BigDecimal total =
                cartService.calculateTotal(1L);

        assertEquals(
                BigDecimal.valueOf(350),
                total);
    }
    
    @Test
    void shouldReturnZeroForEmptyCart() {

        Cart cart = new Cart();
        cart.setItemList(List.of());

        when(cartRepository.findById(1L))
                .thenReturn(Optional.of(cart));

        BigDecimal total =
                cartService.calculateTotal(1L);

        assertEquals(
                BigDecimal.ZERO,
                total);
    }
    
    @Test
    void shouldRemoveItem() {

        CartItem item = new CartItem();
        item.setCartItemId(1L);

        when(cartItemRepository.findById(1L))
                .thenReturn(Optional.of(item));

        cartService.removeItem(1L);

        verify(cartItemRepository)
                .delete(item);
    }
    
    @Test
    void shouldThrowItemNotFound() {

        when(cartItemRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                CartItemNotFoundException.class,
                () -> cartService.removeItem(1L));
    }
}
