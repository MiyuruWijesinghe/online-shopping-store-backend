package com.spm.onlineshopping.service;

import org.springframework.stereotype.Service;
import com.spm.onlineshopping.model.Item;
import com.spm.onlineshopping.resource.ItemAttributeValueAddResource;
import com.spm.onlineshopping.resource.ItemAttributeValueUpdateResource;

@Service
public interface ItemAttributeValueService {

	public Boolean saveItemAttributeValue(Item item, ItemAttributeValueAddResource itemAttributeValueAddResource, Integer index);
	
	public Boolean updateItemAttributeValue(Item item, ItemAttributeValueUpdateResource itemAttributeValueUpdateResource, Integer index);

	public String deleteItemAttributeValue(int id);
}
