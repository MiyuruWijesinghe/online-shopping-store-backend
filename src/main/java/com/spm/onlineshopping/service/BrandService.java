package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spm.onlineshopping.model.Brand;
import com.spm.onlineshopping.resource.BrandResource;

@Service
public interface BrandService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Brand> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	public Optional<Brand> findById(int id);
	
	/**
	 * Find by status.
	 *
	 * @param status the status
	 * @return the list
	 */
	public List<Brand> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the list
	 */
	public List<Brand> findByName(String name);
	
	/**
	 * Save brand.
	 *
	 * @param brandResource the brand resource
	 * @return the integer
	 */
	public Integer saveBrand(BrandResource brandResource);
	
	/**
	 * Update brand.
	 *
	 * @param id the id
	 * @param brandResource the brand resource
	 * @return the brand
	 */
	public Brand updateBrand(int id, BrandResource brandResource);
	
	/**
	 * Delete brand.
	 *
	 * @param id the id
	 * @return the string
	 */
	public String deleteBrand(int id);
}
