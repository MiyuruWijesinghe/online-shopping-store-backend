package com.spm.onlineshopping.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spm.onlineshopping.model.Cart;
import com.spm.onlineshopping.resource.CartAddResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.service.CartService;

@RestController
@RequestMapping(value = "/cart")
@CrossOrigin(origins = "*")
public class CartController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CartService cartService;
	
	
	/**
	 * Gets the all carts.
	 *
	 * @return the all carts
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllCarts() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Cart> cart = cartService.findAll();
		if (!cart.isEmpty()) {
			return new ResponseEntity<>((Collection<Cart>) cart, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the cart by id.
	 *
	 * @param id - the id
	 * @return the cart by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getCartById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Cart> isPresentCart = cartService.findById(id);
		if (isPresentCart.isPresent()) {
			return new ResponseEntity<>(isPresentCart, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the cart by status.
	 *
	 * @param status - the status
	 * @return the cart by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getCartByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Cart> cart = cartService.findByStatus(status);
		if (!cart.isEmpty()) {
			return new ResponseEntity<>((Collection<Cart>) cart, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the cart by username.
	 *
	 * @param username - the username
	 * @return the cart by username
	 */
	@GetMapping(value = "/username/{username}")
	public ResponseEntity<Object> getCartByUsername(@PathVariable(value = "username", required = true) String username) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Cart> cart = cartService.findByUsersUsername(username);
		if (!cart.isEmpty()) {
			return new ResponseEntity<>((Collection<Cart>) cart, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the cart by username and item id and status.
	 *
	 * @param username - the username
	 * @param itemId - the item id
	 * @param status - the status
	 * @return the cart by code and username and item id and status
	 */
	@GetMapping(value = "/{username}/{itemId}/{status}")
	public ResponseEntity<Object> getCartByCodeAndUsernameAndItemIdAndStatus(@PathVariable(value = "username", required = true) String username,
																			 @PathVariable(value = "itemId", required = true) int itemId,
																			 @PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Cart> isPresentCart = cartService.findByUsersUsernameAndItemsIdAndStatus(username, itemId, status);
		if (isPresentCart.isPresent()) {
			return new ResponseEntity<>(isPresentCart, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the cart.
	 *
	 * @param cartAddResource - the cart add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addCart(@Valid @RequestBody CartAddResource cartAddResource) {
		String cartCode = cartService.saveCart(cartAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), cartCode);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Delete cart.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteCart(@PathVariable(value = "id", required = true) int id) {
		String message = cartService.deleteCart(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
}
