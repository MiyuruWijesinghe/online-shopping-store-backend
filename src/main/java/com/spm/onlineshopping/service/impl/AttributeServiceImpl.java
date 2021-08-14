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
import com.spm.onlineshopping.model.Attribute;
import com.spm.onlineshopping.repository.AttributeRepository;
import com.spm.onlineshopping.resource.AttributeResource;
import com.spm.onlineshopping.service.AttributeService;
import com.spm.onlineshopping.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class AttributeServiceImpl implements AttributeService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private AttributeRepository attributeRepository;
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}
	
	private int generateId() {
		List<Attribute> attributeList = attributeRepository.findAll();
		List<Integer> attributeIdList = new ArrayList<>();
		
		for(Attribute attributeObject : attributeList) {
			attributeIdList.add(attributeObject.getId());
		}
		return IdGenerator.generateIDs(attributeIdList);	
	}
	
	@Override
	public List<Attribute> findAll() {
		return attributeRepository.findAll();
	}

	@Override
	public Optional<Attribute> findById(int id) {
		Optional<Attribute> attribute = attributeRepository.findById(id);
		if (attribute.isPresent()) {
			return Optional.ofNullable(attribute.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Attribute> findByStatus(String status) {
		return attributeRepository.findByStatus(status);
	}

	@Override
	public List<Attribute> findByName(String name) {
		return attributeRepository.findByNameContaining(name);
	}

	@Override
	public Integer saveAttribute(AttributeResource attributeResource) {
		Attribute attribute = new Attribute();
		
		Optional<Attribute> isPresentAttribute = attributeRepository.findByName(attributeResource.getName());
        if (isPresentAttribute.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("name.duplicate"), "message");
		}
		
        attribute.setId(generateId());
        attribute.setName(attributeResource.getName());
        attribute.setStatus(attributeResource.getStatus());
        //category.setCreatedUser(authTokenFilter.getUsername());
        attribute.setCreatedUser("MKW");
        attribute.setCreatedDate(formatDate(new Date()));
        attributeRepository.save(attribute);
		return attribute.getId();
	}

	@Override
	public Attribute updateAttribute(int id, AttributeResource attributeResource) {
		Optional<Attribute> isPresentAttribute = attributeRepository.findById(id);
		if (!isPresentAttribute.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<Attribute> isPresentAttributeByName = attributeRepository.findByNameAndIdNotIn(attributeResource.getName(), id);
		if (isPresentAttributeByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("name.duplicate"), "message");
		
		Attribute attribute = isPresentAttribute.get();
		attribute.setName(attributeResource.getName());
		attribute.setStatus(attributeResource.getStatus());
        //category.setModifedUser(authTokenFilter.getUsername());
		attribute.setModifiedUser("MKW");
		attribute.setModifiedDate(formatDate(new Date()));
		attributeRepository.save(attribute);
		return attribute;
	}

	@Override
	public String deleteAttribute(int id) {
		Optional<Attribute> isPresentAttribute = attributeRepository.findById(id);
		if (!isPresentAttribute.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		attributeRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

}
