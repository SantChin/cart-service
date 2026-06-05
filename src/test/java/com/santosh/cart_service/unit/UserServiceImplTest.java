package com.santosh.cart_service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.santosh.cart_service.dto.CreateUserRequest;
import com.santosh.cart_service.dto.UserResponse;
import com.santosh.cart_service.entity.User;
import com.santosh.cart_service.exception.UserNotFoundException;
import com.santosh.cart_service.repository.UserRepository;
import com.santosh.cart_service.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void shouldCreateUserSucess() {

		CreateUserRequest userReq = new CreateUserRequest("santosh", "santosh@gmail.com");

		User savedUser = new User();
		savedUser.setUserId(1L);
		savedUser.setEmail(userReq.email());
		savedUser.setUserName(userReq.userName());

		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		UserResponse responce = userService.createUser(userReq);

		assertNotNull(responce);

		assertEquals(1L, responce.userId());

		assertEquals("santosh", responce.userName());

		verify(userRepository).save(any(User.class));
	}

	@Test
	void shouldReturnUser() {

		User user = new User();
		user.setUserId(1L);
		user.setEmail("santosh@gmail.com");
		user.setUserName("santosh");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		UserResponse response = userService.getUser(1L);

		assertEquals("santosh", response.userName());
	}

	@Test
	void shouldThrowUserNotFoundException() {

		when(userRepository.findById(100L)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.getUser(100L));
	}

}
