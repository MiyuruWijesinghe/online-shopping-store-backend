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

import com.spm.onlineshopping.model.ItemAttributeValue;
import com.spm.onlineshopping.resource.ItemAttributeValueResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.service.ItemAttributeValueService;

@RestController
@RequestMapping(value = "/item-attribute-value")
@CrossOrigin(origins = "*")
public class ItemAttributeValueController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ItemAttributeValueService itemAttributeValueService;
	
	
	/**
	 * Gets the all item attribute values.
	 *
	 * @return the all item attribute values
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllItemAttributeValues() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ItemAttributeValue> itemAttributeValue = itemAttributeValueService.findAll();
		if (!itemAttributeValue.isEmpty()) {
			return new ResponseEntity<>((Collection<ItemAttributeValue>) itemAttributeValue, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the item attribute value by id.
	 *
	 * @param id - the id
	 * @return the item attribute value by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getItemAttributeValueById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ItemAttributeValue> isPresentItemAttributeValue = itemAttributeValueService.findById(id);
		if (isPresentItemAttributeValue.isPresent()) {
			return new ResponseEntity<>(isPresentItemAttributeValue, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the item attribute values by status.
	 *
	 * @param status - the status
	 * @return the item attribute values by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getItemAttributeValuesByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ItemAttributeValue> itemAttributeValue = itemAttributeValueService.findByStatus(status);
		if (!itemAttributeValue.isEmpty()) {
			return new ResponseEntity<>((Collection<ItemAttributeValue>) itemAttributeValue, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Gets the item attribute values by item id.
	 *
	 * @param itemId - the item id
	 * @return the item attribute values by item id
	 */
	@GetMapping(value = "/item/{itemId}")
	public ResponseEntity<Object> getItemAttributeValuesByItemId(@PathVariable(value = "itemId", required = true) int itemId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ItemAttributeValue> itemAttributeValue = itemAttributeValueService.findByItemId(itemId);
		if (!itemAttributeValue.isEmpty()) {
			return new ResponseEntity<>((Collection<ItemAttributeValue>) itemAttributeValue, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Adds the item attribute value.
	 *
	 * @param itemAttributeValueResource - the item attribute value resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addItemAttributeValue(@Valid @RequestBody ItemAttributeValueResource itemAttributeValueResource) {
		Integer itemAttributeValueId = itemAttributeValueService.saveItemAttributeValue(itemAttributeValueResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), itemAttributeValueId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	/**
	 * Update item attribute value.
	 *
	 * @param id - the id
	 * @param itemAttributeValueResource - the item attribute value resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateItemAttributeValue(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody ItemAttributeValueResource itemAttributeValueResource) {
		ItemAttributeValue itemAttributeValue = itemAttributeValueService.updateItemAttributeValue(id, itemAttributeValueResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), itemAttributeValue);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	/**
	 * Delete item attribute value.
	 *
	 * @param id - the  id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteItemAttributeValue(@PathVariable(value = "id", required = true) int id) {
		String message = itemAttributeValueService.deleteItemAttributeValue(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
}
