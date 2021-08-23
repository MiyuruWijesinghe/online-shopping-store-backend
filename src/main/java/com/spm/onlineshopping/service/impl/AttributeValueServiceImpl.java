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
import com.spm.onlineshopping.enums.CommonStatus;
import com.spm.onlineshopping.exception.NoRecordFoundException;
import com.spm.onlineshopping.exception.ValidateRecordException;
import com.spm.onlineshopping.model.Attribute;
import com.spm.onlineshopping.model.AttributeValue;
import com.spm.onlineshopping.repository.AttributeRepository;
import com.spm.onlineshopping.repository.AttributeValueRepository;
import com.spm.onlineshopping.resource.AttributeValueResource;
import com.spm.onlineshopping.service.AttributeValueService;
import com.spm.onlineshopping.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class AttributeValueServiceImpl implements AttributeValueService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private AttributeRepository attributeRepository;
	
	@Autowired
	private AttributeValueRepository attributeValueRepository;
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}
	
	private int generateId() {
		List<AttributeValue> attributeValueList = attributeValueRepository.findAll();
		List<Integer> attributeValueIdList = new ArrayList<>();
		
		for(AttributeValue attributeValueObject : attributeValueList) {
			attributeValueIdList.add(attributeValueObject.getId());
		}
		return IdGenerator.generateIDs(attributeValueIdList);	
	}
	
	@Override
	public List<AttributeValue> findAll() {
		return attributeValueRepository.findAll();
	}

	@Override
	public Optional<AttributeValue> findById(int id) {
		Optional<AttributeValue> attributeValue = attributeValueRepository.findById(id);
		if (attributeValue.isPresent()) {
			return Optional.ofNullable(attributeValue.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<AttributeValue> findByStatus(String status) {
		return attributeValueRepository.findByStatus(status);
	}

	@Override
	public List<AttributeValue> findByName(String name) {
		return attributeValueRepository.findByNameContaining(name);
	}

	@Override
	public List<AttributeValue> findByAttributeId(int attributeId) {
		return attributeValueRepository.findByAttributesId(attributeId);
	}

	@Override
	public Integer saveAttributeValue(AttributeValueResource attributeValueResource) {
		AttributeValue attributeValue = new AttributeValue();
		
		Optional<AttributeValue> isPresentAttributeValue = attributeValueRepository.findByAttributesIdAndName(Integer.parseInt(attributeValueResource.getAttributeId()), attributeValueResource.getName());
        if (isPresentAttributeValue.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("name.duplicate"), "message");
		}
		
        Optional<Attribute> attribute = attributeRepository.findByIdAndStatus(Integer.parseInt(attributeValueResource.getAttributeId()), CommonStatus.ACTIVE.toString());
		if (!attribute.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("attribute.invalid-value"), "message");
		} else {
			attributeValue.setAttributes(attribute.get());
		}
        
        attributeValue.setId(generateId());
        attributeValue.setName(attributeValueResource.getName());
        attributeValue.setStatus(attributeValueResource.getStatus());
        //category.setCreatedUser(authTokenFilter.getUsername());
        attributeValue.setCreatedUser("MKW");
        attributeValue.setCreatedDate(formatDate(new Date()));
        attributeValueRepository.save(attributeValue);
		return attributeValue.getId();
	}

	@Override
	public AttributeValue updateAttributeValue(int id, AttributeValueResource attributeValueResource) {
		Optional<AttributeValue> isPresentAttributeValue = attributeValueRepository.findById(id);
		if (!isPresentAttributeValue.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<AttributeValue> isPresentAttributeValueByName = attributeValueRepository.findByAttributesIdAndNameAndIdNotIn(Integer.parseInt(attributeValueResource.getAttributeId()), attributeValueResource.getName(), id);
		if (isPresentAttributeValueByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("name.duplicate"), "message");
		
		AttributeValue attributeValue = isPresentAttributeValue.get();
		
		Optional<Attribute> attribute = attributeRepository.findByIdAndStatus(Integer.parseInt(attributeValueResource.getAttributeId()), CommonStatus.ACTIVE.toString());
		if (!attribute.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("attribute.invalid-value"), "message");
		} else {
			attributeValue.setAttributes(attribute.get());
		}
		
		attributeValue.setName(attributeValueResource.getName());
		attributeValue.setStatus(attributeValueResource.getStatus());
        //category.setModifedUser(authTokenFilter.getUsername());
		attributeValue.setModifiedUser("MKW");
		attributeValue.setModifiedDate(formatDate(new Date()));
		attributeValueRepository.save(attributeValue);
		return attributeValue;
	}

	@Override
	public String deleteAttributeValue(int id) {
		Optional<AttributeValue> isPresentAttributeValue = attributeValueRepository.findById(id);
		if (!isPresentAttributeValue.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		attributeValueRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}
	
}
