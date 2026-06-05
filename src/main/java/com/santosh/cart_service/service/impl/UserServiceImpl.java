package com.santosh.cart_service.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santosh.cart_service.dto.CreateUserRequest;
import com.santosh.cart_service.dto.UserResponse;
import com.santosh.cart_service.entity.User;
import com.santosh.cart_service.exception.UserNotFoundException;
import com.santosh.cart_service.repository.UserRepository;
import com.santosh.cart_service.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserResponse createUser(CreateUserRequest request) {
		User user = new User();
		user.setUserName(request.userName());
		user.setEmail(request.email());
		User savedUser = userRepository.save(user);
		return new UserResponse(savedUser.getUserId(), savedUser.getUserName(), savedUser.getEmail());
	}

	@Override
	public UserResponse getUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

		return new UserResponse(user.getUserId(), user.getUserName(), user.getEmail());
	}

}
