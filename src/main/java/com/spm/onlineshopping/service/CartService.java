package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spm.onlineshopping.model.Cart;
import com.spm.onlineshopping.resource.CartAddResource;

@Service
public interface CartService {

	public List<Cart> findAll();
	
	public Optional<Cart> findById(int id);
	
	public List<Cart> findByStatus(String status);
	
	public List<Cart> findByUsersUsername(String username);
	
	public Optional<Cart> findByUsersUsernameAndItemsIdAndStatus(String username, int itemId, String status);
	
	public String saveCart(CartAddResource cartAddResource);
	
	public String deleteCart(int id);
	
}
