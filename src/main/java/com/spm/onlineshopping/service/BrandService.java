package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spm.onlineshopping.model.Brand;
import com.spm.onlineshopping.resource.BrandResource;

@Service
public interface BrandService {

	public List<Brand> findAll();
	
	public Optional<Brand> findById(int id);
	
	public List<Brand> findByStatus(String status);
	
	public List<Brand> findByName(String name);
	
	public Integer saveBrand(BrandResource brandResource);
	
	public Brand updateBrand(int id, BrandResource brandResource);
	
	public String deleteBrand(int id);
}
