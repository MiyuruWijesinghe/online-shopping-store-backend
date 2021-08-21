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

import com.spm.onlineshopping.enums.CommonStatus;
import com.spm.onlineshopping.model.Item;
import com.spm.onlineshopping.resource.ItemResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.service.ItemService;

@RestController
@RequestMapping(value = "/item")
@CrossOrigin(origins = "*")
public class ItemController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * Gets the all items.
	 *
	 * @return the all items
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllItems() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Item> item = itemService.findAll();
		if (!item.isEmpty()) {
			return new ResponseEntity<>((Collection<Item>) item, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the item by id.
	 *
	 * @param id - the id
	 * @return the item by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getItemById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Item> isPresentItem = itemService.findById(id);
		if (isPresentItem.isPresent()) {
			return new ResponseEntity<>(isPresentItem, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the item by code.
	 *
	 * @param code - the code
	 * @return the item by code
	 */
	@GetMapping(value = "/code/{code}")
	public ResponseEntity<Object> getItemByCode(@PathVariable(value = "code", required = true) String code) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Item> isPresentItem = itemService.findByCode(code);
		if (isPresentItem.isPresent()) {
			return new ResponseEntity<>(isPresentItem, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the items by status.
	 *
	 * @param status - the status
	 * @return the items by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getItemsByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Item> item = itemService.findByStatus(status);
		if (!item.isEmpty()) {
			return new ResponseEntity<>((Collection<Item>) item, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the items by name.
	 *
	 * @param name - the name
	 * @return the items by name
	 */
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getItemsByName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Item> item = itemService.findByName(name);
		if (!item.isEmpty()) {
			return new ResponseEntity<>((Collection<Item>) item, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the items by category id.
	 *
	 * @param categoryId - the category id
	 * @return the items by category id
	 */
	@GetMapping(value = "/category/{categoryId}")
	public ResponseEntity<Object> getItemsByCategoryId(@PathVariable(value = "categoryId", required = true) int categoryId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Item> item = itemService.findByCategoryIdAndStatus(categoryId, CommonStatus.ACTIVE.toString());
		if (!item.isEmpty()) {
			return new ResponseEntity<>((Collection<Item>) item, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the item.
	 *
	 * @param itemResource - the item resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addItem(@Valid @RequestBody ItemResource itemResource) {
		Integer itemId = itemService.saveItem(itemResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), itemId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update item.
	 *
	 * @param id - the id
	 * @param itemResource - the item resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateItem(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody ItemResource itemResource) {
		Item item = itemService.updateItem(id, itemResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), item);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete item.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteItem(@PathVariable(value = "id", required = true) int id) {
		String message = itemService.deleteItem(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}

}
