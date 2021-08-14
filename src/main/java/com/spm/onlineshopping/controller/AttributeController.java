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
import com.spm.onlineshopping.model.Attribute;
import com.spm.onlineshopping.resource.AttributeResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.service.AttributeService;

@RestController
@RequestMapping(value = "/attribute")
@CrossOrigin(origins = "*")
public class AttributeController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private AttributeService attributeService;

	
	/**
	 * Gets the all attributes.
	 *
	 * @return the all attributes
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllAttributes() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Attribute> attribute = attributeService.findAll();
		if (!attribute.isEmpty()) {
			return new ResponseEntity<>((Collection<Attribute>) attribute, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the attribute by id.
	 *
	 * @param id - the id
	 * @return the attribute by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getAttributeById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Attribute> isPresentAttribute = attributeService.findById(id);
		if (isPresentAttribute.isPresent()) {
			return new ResponseEntity<>(isPresentAttribute, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the attributes by status.
	 *
	 * @param status - the status
	 * @return the attributes by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getAttributesByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Attribute> attribute = attributeService.findByStatus(status);
		if (!attribute.isEmpty()) {
			return new ResponseEntity<>((Collection<Attribute>) attribute, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
		
	
	/**
	 * Gets the attributes by name.
	 *
	 * @param name - the name
	 * @return the attributes by name
	 */
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getAttributesByName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Attribute> attribute = attributeService.findByName(name);
		if (!attribute.isEmpty()) {
			return new ResponseEntity<>((Collection<Attribute>) attribute, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the attribute.
	 *
	 * @param attributeResource - the attribute resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addAttribute(@Valid @RequestBody AttributeResource attributeResource) {
		Integer attributeId = attributeService.saveAttribute(attributeResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), attributeId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update attribute.
	 *
	 * @param id - the id
	 * @param attributeResource - the attribute resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateAttribute(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody AttributeResource attributeResource) {
		Attribute attribute = attributeService.updateAttribute(id, attributeResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), attribute);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete attribute.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteAttribute(@PathVariable(value = "id", required = true) int id) {
		String message = attributeService.deleteAttribute(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
}
