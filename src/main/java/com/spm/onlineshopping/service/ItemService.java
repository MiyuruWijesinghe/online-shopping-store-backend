package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spm.onlineshopping.model.Item;
import com.spm.onlineshopping.resource.ItemResource;

@Service
public interface ItemService {
	

	public List<Item> findAll();
	
	public Optional<Item> findById(int id);
	
	public Optional<Item> findByCode(String code);
	
	public List<Item> findByStatus(String status);
	
	public List<Item> findByName(String name);
	
	public List<Item> findByCategoryIdAndStatus(int categoryId, String status);
	
	public Integer saveItem(ItemResource itemResource);
	
	public Item updateItem(int id, ItemResource itemResource);
	
	public String deleteItem(int id);
}
