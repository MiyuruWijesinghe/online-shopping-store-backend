package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spm.onlineshopping.model.Category;
import com.spm.onlineshopping.resource.CategoryResource;


@Service
public interface CategoryService {

	public List<Category> findAll();
	
	public Optional<Category> findById(int id);
	
	public List<Category> findByStatus(String status);
	
	public List<Category> findByName(String name);
	
	public Integer saveCategory(CategoryResource categoryResource);
	
	public Category updateCategory(int id, CategoryResource categoryResource);
	
	public String deleteCategory(int id);
	
}
