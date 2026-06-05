package com.santosh.cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santosh.cart_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
