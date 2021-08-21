package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spm.onlineshopping.model.AttributeValue;
import com.spm.onlineshopping.resource.AttributeValueResource;

@Service
public interface AttributeValueService {

	public List<AttributeValue> findAll();
	
	public Optional<AttributeValue> findById(int id);
	
	public List<AttributeValue> findByStatus(String status);
	
	public List<AttributeValue> findByName(String name);
	
	public List<AttributeValue> findByAttributeId(int attributeId);
	
	public Integer saveAttributeValue(AttributeValueResource attributeValueResource);
	
	public AttributeValue updateAttributeValue(int id, AttributeValueResource attributeValueResource);
	
	public String deleteAttributeValue(int id);
	
}
