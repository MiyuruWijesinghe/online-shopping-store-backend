package com.spm.onlineshopping.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.spm.onlineshopping.enums.CommonStatus;
import com.spm.onlineshopping.exception.NoRecordFoundException;
import com.spm.onlineshopping.model.Cart;
import com.spm.onlineshopping.model.Item;
import com.spm.onlineshopping.model.Users;
import com.spm.onlineshopping.repository.CartRepository;
import com.spm.onlineshopping.repository.ItemRepository;
import com.spm.onlineshopping.repository.UserRepository;
import com.spm.onlineshopping.resource.CartAddResource;
import com.spm.onlineshopping.resource.CartListResource;
import com.spm.onlineshopping.service.CartService;
import com.spm.onlineshopping.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class CartServiceImpl implements CartService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}
	
	private int generateId() {
		List<Cart> cartList = cartRepository.findAll();
		List<Integer> cartIdList = new ArrayList<>();
		
		for(Cart cartObject : cartList) {
			cartIdList.add(cartObject.getId());
		}
		return IdGenerator.generateIDs(cartIdList);	
	}
	
	@Override
	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	@Override
	public Optional<Cart> findById(int id) {
		Optional<Cart> cart = cartRepository.findById(id);
		if (cart.isPresent()) {
			return Optional.ofNullable(cart.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Cart> findByStatus(String status) {
		return cartRepository.findByStatus(status);
	}

	@Override
	public List<Cart> findByUsersUsername(String username) {
		return cartRepository.findByUsersUsernameAndStatus(username, CommonStatus.ACTIVE.toString());
	}

	@Override
	public Optional<Cart> findByUsersUsernameAndItemsIdAndStatus(String username, int itemId, String status) {
		Optional<Cart> cart = cartRepository.findByUsersUsernameAndItemsIdAndStatus(username, itemId, status);
		if (cart.isPresent()) {
			return Optional.ofNullable(cart.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public String saveCart(CartAddResource cartAddResource) {
		
		Cart cart = new Cart();
		
		String username = cartAddResource.getUserName();
		
		for(CartListResource cartObject : cartAddResource.getCartItems()) {
			cart.setId(generateId());
			cart.setStatus(CommonStatus.ACTIVE.toString());
			
			Optional<Users> users = userRepository.findByUsername(username);
			if (users.isPresent()) {
				cart.setUsers(users.get());
			}
			
			Optional<Item> item = itemRepository.findByIdAndStatus(Integer.parseInt(cartObject.getItemId()), CommonStatus.ACTIVE.toString());
			if (item.isPresent()) {
				cart.setItems(item.get());
			}
			
			cart.setQuantity(Long.parseLong(cartObject.getQuantity()));
			cart.setCreatedDate(formatDate(new Date()));
			cartRepository.save(cart);
		}
		
		return "Item added to cart.";
	}

	@Override
	public String deleteCart(int id) {
		Optional<Cart> isPresentCart = cartRepository.findById(id);
		if (!isPresentCart.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		cartRepository.deleteById(id);
		return environment.getProperty("cart-item.deleted");
	}

	
}
