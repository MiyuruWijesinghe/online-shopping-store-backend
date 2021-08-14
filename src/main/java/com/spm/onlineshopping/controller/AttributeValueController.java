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
import com.spm.onlineshopping.model.AttributeValue;
import com.spm.onlineshopping.resource.AttributeValueResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.service.AttributeValueService;

@RestController
@RequestMapping(value = "/attribute-value")
@CrossOrigin(origins = "*")
public class AttributeValueController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private AttributeValueService attributeValueService;
	
	/**
	 * Gets the all attribute values.
	 *
	 * @return the all attribute values
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllAttributeValues() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<AttributeValue> attributeValue = attributeValueService.findAll();
		if (!attributeValue.isEmpty()) {
			return new ResponseEntity<>((Collection<AttributeValue>) attributeValue, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the attribute value by id.
	 *
	 * @param id - the id
	 * @return the attribute value by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getAttributeValueById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<AttributeValue> isPresentAttributeValue = attributeValueService.findById(id);
		if (isPresentAttributeValue.isPresent()) {
			return new ResponseEntity<>(isPresentAttributeValue, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the attribute values by status.
	 *
	 * @param status - the status
	 * @return the attribute values by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getAttributeValuesByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<AttributeValue> attributeValue = attributeValueService.findByStatus(status);
		if (!attributeValue.isEmpty()) {
			return new ResponseEntity<>((Collection<AttributeValue>) attributeValue, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the attribute values by name.
	 *
	 * @param name - the name
	 * @return the attribute values by name
	 */
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getAttributeValuesByName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<AttributeValue> attributeValue = attributeValueService.findByName(name);
		if (!attributeValue.isEmpty()) {
			return new ResponseEntity<>((Collection<AttributeValue>) attributeValue, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the attribute values by attribute id.
	 *
	 * @param attributeId - the attribute id
	 * @return the attribute values by attribute id
	 */
	@GetMapping(value = "/attribute/{attributeId}")
	public ResponseEntity<Object> getAttributeValuesByAttributeId(@PathVariable(value = "attributeId", required = true) Long attributeId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<AttributeValue> attributeValue = attributeValueService.findByAttributeId(attributeId);
		if (!attributeValue.isEmpty()) {
			return new ResponseEntity<>((Collection<AttributeValue>) attributeValue, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the attribute value.
	 *
	 * @param attributeValueResource - the attribute value resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addAttributeValue(@Valid @RequestBody AttributeValueResource attributeValueResource) {
		Integer attributeValueId = attributeValueService.saveAttributeValue(attributeValueResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), attributeValueId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update attribute value.
	 *
	 * @param id - the id
	 * @param attributeValueResource - the attribute value resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateAttributeValue(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody AttributeValueResource attributeValueResource) {
		AttributeValue attributeValue = attributeValueService.updateAttributeValue(id, attributeValueResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), attributeValue);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete attribute value.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteAttributeValue(@PathVariable(value = "id", required = true) int id) {
		String message = attributeValueService.deleteAttributeValue(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
}
