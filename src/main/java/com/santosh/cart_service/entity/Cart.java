package com.santosh.cart_service.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CartItem> itemList = new ArrayList<>();

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<CartItem> itemList) {
		this.itemList = itemList;
	}
	
	
	
	public void addItem(CartItem item) {
	    itemList.add(item);
	    item.setCart(this);
	}

	public void removeItem(CartItem item) {
	    itemList.remove(item);
	    item.setCart(null);
	}
	
}
