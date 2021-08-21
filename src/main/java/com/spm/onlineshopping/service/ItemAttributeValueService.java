package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spm.onlineshopping.model.ItemAttributeValue;
import com.spm.onlineshopping.resource.ItemAttributeValueResource;

@Service
public interface ItemAttributeValueService {
	
	public List<ItemAttributeValue> findAll();

	public Optional<ItemAttributeValue> findById(int id);
	
	public List<ItemAttributeValue> findByStatus(String status);
	
	public List<ItemAttributeValue> findByItemId(int itemId);
	
	public Integer saveItemAttributeValue(ItemAttributeValueResource itemAttributeValueResource);
	
	public ItemAttributeValue updateItemAttributeValue(int id, ItemAttributeValueResource itemAttributeValueResource);

	public String deleteItemAttributeValue(int id);
}
