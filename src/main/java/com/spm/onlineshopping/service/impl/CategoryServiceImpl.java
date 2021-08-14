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
import com.spm.onlineshopping.model.Category;
import com.spm.onlineshopping.repository.CategoryRepository;
import com.spm.onlineshopping.resource.CategoryResource;
import com.spm.onlineshopping.service.CategoryService;
import com.spm.onlineshopping.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	/*@Autowired
	private AuthTokenFilter authTokenFilter;*/
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}
	
	private int generateId() {
		List<Category> categoryList = categoryRepository.findAll();
		List<Integer> categoryIdList = new ArrayList<>();
		
		for(Category categoryObject : categoryList) {
			categoryIdList.add(categoryObject.getId());
		}
		return IdGenerator.generateIDs(categoryIdList);	
	}
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> findById(int id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			return Optional.ofNullable(category.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Category> findByStatus(String status) {
		return categoryRepository.findByStatus(status);
	}

	@Override
	public List<Category> findByName(String name) {
		return categoryRepository.findByNameContaining(name);
	}

	@Override
	public Integer saveCategory(CategoryResource categoryResource) {
		Category category = new Category();
		
		Optional<Category> isPresentCategory = categoryRepository.findByName(categoryResource.getName());
        if (isPresentCategory.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("name.duplicate"), "message");
		}
		
        category.setId(generateId());
        category.setName(categoryResource.getName());
        category.setDescription(categoryResource.getDescription());
        category.setImageURL(categoryResource.getImageURL());
        category.setStatus(categoryResource.getStatus());
        //category.setCreatedUser(authTokenFilter.getUsername());
        category.setCreatedUser("MKW");
        category.setCreatedDate(formatDate(new Date()));
        categoryRepository.save(category);
		return category.getId();
	}

	@Override
	public Category updateCategory(int id, CategoryResource categoryResource) {
		Optional<Category> isPresentCategory = categoryRepository.findById(id);
		if (!isPresentCategory.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<Category> isPresentCategoryByName = categoryRepository.findByNameAndIdNotIn(categoryResource.getName(), id);
		if (isPresentCategoryByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("name.duplicate"), "message");
		
		Category category = isPresentCategory.get();
		category.setName(categoryResource.getName());
        category.setDescription(categoryResource.getDescription());
        category.setImageURL(categoryResource.getImageURL());
        category.setStatus(categoryResource.getStatus());
        //category.setModifedUser(authTokenFilter.getUsername());
        category.setModifiedUser("MKW");
        category.setModifiedDate(formatDate(new Date()));
        categoryRepository.save(category);
		return category;
	}

	@Override
	public String deleteCategory(int id) {
		Optional<Category> isPresentCategory = categoryRepository.findById(id);
		if (!isPresentCategory.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		categoryRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}
	
}
