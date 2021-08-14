package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spm.onlineshopping.model.Attribute;
import com.spm.onlineshopping.resource.AttributeResource;

@Service
public interface AttributeService {

	public List<Attribute> findAll();
	
	public Optional<Attribute> findById(int id);
	
	public List<Attribute> findByStatus(String status);
	
	public List<Attribute> findByName(String name);
	
	public Integer saveAttribute(AttributeResource attributeResource);
	
	public Attribute updateAttribute(int id, AttributeResource attributeResource);
	
	public String deleteAttribute(int id);
	
}
