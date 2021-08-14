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

import com.spm.onlineshopping.model.Brand;
import com.spm.onlineshopping.resource.BrandResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.service.BrandService;

@RestController
@RequestMapping(value = "/brand")
@CrossOrigin(origins = "*")
public class BrandController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BrandService brandService;

	
	/**
	 * Gets the all brands.
	 *
	 * @return the all brands
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllBrands() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Brand> brand = brandService.findAll();
		if (!brand.isEmpty()) {
			return new ResponseEntity<>((Collection<Brand>) brand, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the brand by id.
	 *
	 * @param id - the id
	 * @return the brand by id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getBrandById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Brand> isPresentBrand = brandService.findById(id);
		if (isPresentBrand.isPresent()) {
			return new ResponseEntity<>(isPresentBrand, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Gets the brands by status.
	 *
	 * @param status - the status
	 * @return the brands by status
	 */
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getBrandsByStatus(@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Brand> brand = brandService.findByStatus(status);
		if (!brand.isEmpty()) {
			return new ResponseEntity<>((Collection<Brand>) brand, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
		
	
	/**
	 * Gets the brands by name.
	 *
	 * @param name - the name
	 * @return the brands by name
	 */
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getBrandsByName(@PathVariable(value = "name", required = true) String name) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Brand> brand = brandService.findByName(name);
		if (!brand.isEmpty()) {
			return new ResponseEntity<>((Collection<Brand>) brand, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Adds the brand.
	 *
	 * @param brandResource - the brand resource
	 * @return the response entity
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Object> addBrand(@Valid @RequestBody BrandResource brandResource) {
		Integer brandId = brandService.saveBrand(brandResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), brandId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	/**
	 * Update brand.
	 *
	 * @param id - the id
	 * @param brandResource - the brand resource
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateBrand(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody BrandResource brandResource) {
		Brand brand = brandService.updateBrand(id, brandResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), brand);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	/**
	 * Delete brand.
	 *
	 * @param id - the id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteBrand(@PathVariable(value = "id", required = true) int id) {
		String message = brandService.deleteBrand(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
}
