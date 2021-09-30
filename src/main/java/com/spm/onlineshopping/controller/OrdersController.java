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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spm.onlineshopping.model.Orders;
import com.spm.onlineshopping.resource.OrdersAddResource;
import com.spm.onlineshopping.resource.OrdersUpdateResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.service.OrdersService;

@RestController
@RequestMapping(value = "/orders")
@CrossOrigin(origins = "*")
public class OrdersController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private OrdersService ordersService;
	
	
	/**
	 * Gets the all orders.
	 *
	 * @return the all orders
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllOrders() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Orders> orders = ordersService.findAll();
		if (!orders.isEmpty()) {
			return new ResponseEntity<>((Collection<Orders>) orders, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the order by id.
	 *
	 * @param id - the id
	 * @return the order by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getOrderById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Orders> isPresentOrders = ordersService.findById(id);
		if (isPresentOrders.isPresent()) {
			return new ResponseEntity<>(isPresentOrders, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the orders by status.
	 *
	 * @param status - the status
	 * @return the orders by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getOrdersByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Orders> orders = ordersService.findByStatus(status);
		if (!orders.isEmpty()) {
			return new ResponseEntity<>((Collection<Orders>) orders, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the orders by username.
	 *
	 * @param username - the username
	 * @return the orders by username
	 */
	@GetMapping(value = "/username/{username}")
	public ResponseEntity<Object> getOrdersByUsername(@PathVariable(value = "username", required = true) String username) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Orders> orders = ordersService.findByUsersUsername(username);
		if (!orders.isEmpty()) {
			return new ResponseEntity<>((Collection<Orders>) orders, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the orders by reference code.
	 *
	 * @param referenceCode - the reference code
	 * @return the orders by reference code
	 */
	@GetMapping(value = "/referenceCode/{referenceCode}")
	public ResponseEntity<Object> getOrdersByReferenceCode(@PathVariable(value = "referenceCode", required = true) String referenceCode) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Orders> orders = ordersService.findByReferenceCode(referenceCode);
		if (!orders.isEmpty()) {
			return new ResponseEntity<>((Collection<Orders>) orders, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the orders by reference code and username and item id and status.
	 *
	 * @param referenceCode - the reference code
	 * @param username - the username
	 * @param itemId - the item id
	 * @param status - the status
	 * @return the orders by reference code and username and item id and status
	 */
	@GetMapping(value = "/{referenceCode}/{username}/{itemId}/{status}")
	public ResponseEntity<Object> getOrdersByReferenceCodeAndUsernameAndItemIdAndStatus(@PathVariable(value = "referenceCode", required = true) String referenceCode,
																						@PathVariable(value = "username", required = true) String username,
																						@PathVariable(value = "itemId", required = true) int itemId,
																						@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Orders> isPresentOrders = ordersService.findByReferenceCodeAndUsersUsernameAndItemsIdAndStatus(referenceCode, username, itemId, status);
		if (isPresentOrders.isPresent()) {
			return new ResponseEntity<>(isPresentOrders, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the orders.
	 *
	 * @param ordersAddResource - the orders add resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addOrders(@Valid @RequestBody OrdersAddResource ordersAddResource) {
		String orderRefCode = ordersService.saveOrders(ordersAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), orderRefCode);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update orders.
	 *
	 * @param referenceCode - the reference code
	 * @param ordersUpdateResource - the orders update resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{referenceCode}")
	public ResponseEntity<Object> updateOrders(@PathVariable(value = "referenceCode", required = true) String referenceCode,
											   @Valid @RequestBody OrdersUpdateResource ordersUpdateResource) {
		ordersService.updateOrders(referenceCode, ordersUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"));
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete orders.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteOrders(@PathVariable(value = "id", required = true) int id) {
		String message = ordersService.deleteOrders(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
}
