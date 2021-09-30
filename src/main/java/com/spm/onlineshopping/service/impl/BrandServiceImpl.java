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

import com.spm.onlineshopping.exception.NoRecordFoundException;
import com.spm.onlineshopping.exception.ValidateRecordException;
import com.spm.onlineshopping.model.Brand;
import com.spm.onlineshopping.repository.BrandRepository;
import com.spm.onlineshopping.resource.BrandResource;
import com.spm.onlineshopping.security.jwt.AuthTokenFilter;
import com.spm.onlineshopping.service.BrandService;
import com.spm.onlineshopping.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class BrandServiceImpl implements BrandService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private AuthTokenFilter authTokenFilter;
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}
	
	private int generateId() {
		List<Brand> brandList = brandRepository.findAll();
		List<Integer> brandIdList = new ArrayList<>();
		
		for(Brand brandObject : brandList) {
			brandIdList.add(brandObject.getId());
		}
		return IdGenerator.generateIDs(brandIdList);	
	}
	
	@Override
	public List<Brand> findAll() {
		return brandRepository.findAll();
	}

	@Override
	public Optional<Brand> findById(int id) {
		Optional<Brand> brand = brandRepository.findById(id);
		if (brand.isPresent()) {
			return Optional.ofNullable(brand.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Brand> findByStatus(String status) {
		return brandRepository.findByStatus(status);
	}

	@Override
	public List<Brand> findByName(String name) {
		return brandRepository.findByNameContaining(name);
	}

	@Override
	public Integer saveBrand(BrandResource brandResource) {
		Brand brand = new Brand();
		
		Optional<Brand> isPresentCategory = brandRepository.findByName(brandResource.getName());
        if (isPresentCategory.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("name.duplicate"), "message");
		}
		
        brand.setId(generateId());
        brand.setName(brandResource.getName());
        brand.setImageURL(brandResource.getImageURL());
        brand.setStatus(brandResource.getStatus());
        brand.setCreatedUser(authTokenFilter.getUsername());
        brand.setCreatedDate(formatDate(new Date()));
        brandRepository.save(brand);
		return brand.getId();
	}

	@Override
	public Brand updateBrand(int id, BrandResource brandResource) {
		Optional<Brand> isPresentBrand = brandRepository.findById(id);
		if (!isPresentBrand.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<Brand> isPresentBrandByName = brandRepository.findByNameAndIdNotIn(brandResource.getName(), id);
		if (isPresentBrandByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("name.duplicate"), "message");
		
		Brand brand = isPresentBrand.get();
		brand.setName(brandResource.getName());
		brand.setImageURL(brandResource.getImageURL());
        brand.setStatus(brandResource.getStatus());
        brand.setModifiedUser(authTokenFilter.getUsername());
        brand.setModifiedDate(formatDate(new Date()));
        brandRepository.save(brand);
		return brand;
	}

	@Override
	public String deleteBrand(int id) {
		Optional<Brand> isPresentBrand = brandRepository.findById(id);
		if (!isPresentBrand.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		brandRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

	
}
