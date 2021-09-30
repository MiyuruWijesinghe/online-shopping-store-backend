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
import com.spm.onlineshopping.model.AttributeValue;
import com.spm.onlineshopping.model.Item;
import com.spm.onlineshopping.model.ItemAttributeValue;
import com.spm.onlineshopping.repository.AttributeValueRepository;
import com.spm.onlineshopping.repository.ItemAttributeValueRepository;
import com.spm.onlineshopping.repository.ItemRepository;
import com.spm.onlineshopping.resource.ItemAttributeValueResource;
import com.spm.onlineshopping.security.jwt.AuthTokenFilter;
import com.spm.onlineshopping.service.ItemAttributeValueService;
import com.spm.onlineshopping.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class ItemAttributeValueServiceImpl implements ItemAttributeValueService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ItemAttributeValueRepository itemAttributeValueRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private AttributeValueRepository attributeValueRepository;
	
	@Autowired
	private AuthTokenFilter authTokenFilter;
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}
	
	private int generateId() {
		List<ItemAttributeValue> itemAttributeValueList = itemAttributeValueRepository.findAll();
		List<Integer> itemAttributeValueIdList = new ArrayList<>();
		
		for(ItemAttributeValue itemAttributeValueObject : itemAttributeValueList) {
			itemAttributeValueIdList.add(itemAttributeValueObject.getId());
		}
		return IdGenerator.generateIDs(itemAttributeValueIdList);	
	}

	@Override
	public List<ItemAttributeValue> findAll() {
		return itemAttributeValueRepository.findAll();
	}
	
	@Override
	public Optional<ItemAttributeValue> findById(int id) {
		Optional<ItemAttributeValue> itemAttributeValue = itemAttributeValueRepository.findById(id);
		if (itemAttributeValue.isPresent()) {
			return Optional.ofNullable(itemAttributeValue.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ItemAttributeValue> findByStatus(String status) {
		return itemAttributeValueRepository.findByStatus(status);
	}

	@Override
	public List<ItemAttributeValue> findByItemId(int itemId) {
		return itemAttributeValueRepository.findByItemsId(itemId);
	}
	
	@Override
	public Integer saveItemAttributeValue(ItemAttributeValueResource itemAttributeValueResource) {
		ItemAttributeValue itemAttributeValue = new ItemAttributeValue();
		
		Optional<ItemAttributeValue> isPresentItemAttributeValue = itemAttributeValueRepository.findByItemsIdAndAttributeValuesId(Integer.parseInt(itemAttributeValueResource.getItemId()), Integer.parseInt(itemAttributeValueResource.getAttributeValueId()));
        if (isPresentItemAttributeValue.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("itemAttributeValue.duplicate"), "message");
        }
		
        Optional<Item> item = itemRepository.findByIdAndStatus(Integer.parseInt(itemAttributeValueResource.getItemId()), CommonStatus.ACTIVE.toString());
		if (!item.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("item.invalid-value"), "message");
		} else {
			itemAttributeValue.setItems(item.get());
		}
        
        Optional<AttributeValue> attributeValue = attributeValueRepository.findByIdAndStatus(Integer.parseInt(itemAttributeValueResource.getAttributeValueId()), CommonStatus.ACTIVE.toString());
		if (!attributeValue.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("attribute-value.invalid-value"), "message");
		} else {
			itemAttributeValue.setAttributeValues(attributeValue.get());
		}
        
		itemAttributeValue.setId(generateId());
		itemAttributeValue.setStatus(itemAttributeValueResource.getStatus());
		itemAttributeValue.setCreatedUser(authTokenFilter.getUsername());
		itemAttributeValue.setCreatedDate(formatDate(new Date()));
		itemAttributeValueRepository.save(itemAttributeValue);
		
		return itemAttributeValue.getId();
	}

	@Override
	public ItemAttributeValue updateItemAttributeValue(int id, ItemAttributeValueResource itemAttributeValueResource) {
		Optional<ItemAttributeValue> isPresentItemAttributeValue = itemAttributeValueRepository.findById(id);
		if (!isPresentItemAttributeValue.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<ItemAttributeValue> isPresentItemAttributeDuplicate = itemAttributeValueRepository.findByItemsIdAndAttributeValuesIdAndIdNotIn(Integer.parseInt(itemAttributeValueResource.getItemId()), Integer.parseInt(itemAttributeValueResource.getAttributeValueId()), id);
        if (isPresentItemAttributeDuplicate.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("itemAttributeValue.duplicate"), "message");
		}
		
        ItemAttributeValue itemAttributeValue = isPresentItemAttributeValue.get();
		
        Optional<Item> item = itemRepository.findByIdAndStatus(Integer.parseInt(itemAttributeValueResource.getItemId()), CommonStatus.ACTIVE.toString());
		if (!item.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("item.invalid-value"), "message");
		} else {
			itemAttributeValue.setItems(item.get());
		}
        
        Optional<AttributeValue> attributeValue = attributeValueRepository.findByIdAndStatus(Integer.parseInt(itemAttributeValueResource.getAttributeValueId()), CommonStatus.ACTIVE.toString());
		if (!attributeValue.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("attribute-value.invalid-value"), "message");
		} else {
			itemAttributeValue.setAttributeValues(attributeValue.get());
		}
		
		itemAttributeValue.setStatus(itemAttributeValueResource.getStatus());
		itemAttributeValue.setModifiedUser(authTokenFilter.getUsername());
		itemAttributeValue.setModifiedDate(formatDate(new Date()));
		itemAttributeValueRepository.save(itemAttributeValue);
		
		return itemAttributeValue;
	}

	@Override
	public String deleteItemAttributeValue(int id) {
		Optional<ItemAttributeValue> isPresentItemAttributeValue = itemAttributeValueRepository.findById(id);
		if (!isPresentItemAttributeValue.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		itemAttributeValueRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}
	
}
