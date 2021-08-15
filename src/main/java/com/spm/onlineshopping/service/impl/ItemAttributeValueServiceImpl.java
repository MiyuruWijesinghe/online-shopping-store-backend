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
import com.spm.onlineshopping.enums.ServiceEntity;
import com.spm.onlineshopping.enums.TransferType;
import com.spm.onlineshopping.exception.InvalidDetailListServiceIdException;
import com.spm.onlineshopping.exception.NoRecordFoundException;
import com.spm.onlineshopping.model.AttributeValue;
import com.spm.onlineshopping.model.Item;
import com.spm.onlineshopping.model.ItemAttributeValue;
import com.spm.onlineshopping.repository.AttributeValueRepository;
import com.spm.onlineshopping.repository.ItemAttributeValueRepository;
import com.spm.onlineshopping.resource.ItemAttributeValueAddResource;
import com.spm.onlineshopping.resource.ItemAttributeValueUpdateResource;
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
	private AttributeValueRepository attributeValueRepository;
	
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
	public Boolean saveItemAttributeValue(Item item, ItemAttributeValueAddResource itemAttributeValueAddResource, Integer index) {
		ItemAttributeValue itemAttributeValue = new ItemAttributeValue();
		
		Optional<ItemAttributeValue> isPresentItemAttributeValue = itemAttributeValueRepository.findByItemsIdAndAttributeValuesId(item.getId(), Integer.parseInt(itemAttributeValueAddResource.getAttributeValueId()));
        if (isPresentItemAttributeValue.isPresent()) {
        	throw new InvalidDetailListServiceIdException(environment.getProperty("itemAttributeValue.duplicate"), ServiceEntity.ATTRIBUTE_VALUE_ID, TransferType.ITEM_ATTRIBUTE_VALUE_SAVE, index);
		}
		
        Optional<AttributeValue> attributeValue = attributeValueRepository.findByIdAndStatus(Integer.parseInt(itemAttributeValueAddResource.getAttributeValueId()), CommonStatus.ACTIVE.toString());
		if (!attributeValue.isPresent()) {
			throw new InvalidDetailListServiceIdException(environment.getProperty("attribute-value.invalid-value"), ServiceEntity.ATTRIBUTE_VALUE_ID, TransferType.ITEM_ATTRIBUTE_VALUE_SAVE, index);
		} else {
			itemAttributeValue.setAttributeValues(attributeValue.get());
		}
        
		itemAttributeValue.setId(generateId());
		itemAttributeValue.setItems(item);
		itemAttributeValue.setStatus(itemAttributeValueAddResource.getStatus());
        //category.setCreatedUser(authTokenFilter.getUsername());
		itemAttributeValue.setCreatedUser("MKW");
		itemAttributeValue.setCreatedDate(formatDate(new Date()));
		itemAttributeValueRepository.save(itemAttributeValue);
		
		return Boolean.TRUE;
	}

	@Override
	public Boolean updateItemAttributeValue(Item item, ItemAttributeValueUpdateResource itemAttributeValueUpdateResource, Integer index) {
		Optional<ItemAttributeValue> isPresentItemAttributeValue = itemAttributeValueRepository.findById(Integer.parseInt(itemAttributeValueUpdateResource.getId()));
		if (!isPresentItemAttributeValue.isPresent()) {
			throw new InvalidDetailListServiceIdException(environment.getProperty("common.record-not-found"), ServiceEntity.ID, TransferType.ITEM_ATTRIBUTE_VALUE_UPDATE, index);
		}
		
		Optional<ItemAttributeValue> isPresentItemAttributeDuplicate = itemAttributeValueRepository.findByItemsIdAndAttributeValuesIdAndIdNotIn(item.getId(), Integer.parseInt(itemAttributeValueUpdateResource.getAttributeValueId()), Integer.parseInt(itemAttributeValueUpdateResource.getId()));
        if (isPresentItemAttributeDuplicate.isPresent()) {
        	throw new InvalidDetailListServiceIdException(environment.getProperty("itemAttributeValue.duplicate"), ServiceEntity.ATTRIBUTE_VALUE_ID, TransferType.ITEM_ATTRIBUTE_VALUE_UPDATE, index);
		}
		
        ItemAttributeValue itemAttributeValue = isPresentItemAttributeValue.get();
		
        Optional<AttributeValue> attributeValue = attributeValueRepository.findByIdAndStatus(Integer.parseInt(itemAttributeValueUpdateResource.getAttributeValueId()), CommonStatus.ACTIVE.toString());
		if (!attributeValue.isPresent()) {
			throw new InvalidDetailListServiceIdException(environment.getProperty("attribute-value.invalid-value"), ServiceEntity.ATTRIBUTE_VALUE_ID, TransferType.ITEM_ATTRIBUTE_VALUE_UPDATE, index);
		} else {
			itemAttributeValue.setAttributeValues(attributeValue.get());
		}
		
		itemAttributeValue.setItems(item);
		itemAttributeValue.setStatus(itemAttributeValueUpdateResource.getStatus());
        //category.setModifedUser(authTokenFilter.getUsername());
		itemAttributeValue.setModifiedUser("MKW");
		itemAttributeValue.setModifiedDate(formatDate(new Date()));
		itemAttributeValueRepository.save(itemAttributeValue);
		
		return Boolean.TRUE;
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
