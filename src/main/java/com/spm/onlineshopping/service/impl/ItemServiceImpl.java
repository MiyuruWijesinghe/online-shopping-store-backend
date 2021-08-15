package com.spm.onlineshopping.service.impl;

import java.math.BigDecimal;
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
import com.spm.onlineshopping.model.Brand;
import com.spm.onlineshopping.model.Category;
import com.spm.onlineshopping.model.Item;
import com.spm.onlineshopping.model.ItemAttributeValue;
import com.spm.onlineshopping.repository.BrandRepository;
import com.spm.onlineshopping.repository.CategoryRepository;
import com.spm.onlineshopping.repository.ItemAttributeValueRepository;
import com.spm.onlineshopping.repository.ItemRepository;
import com.spm.onlineshopping.resource.ItemAddResource;
import com.spm.onlineshopping.resource.ItemAttributeValueAddResource;
import com.spm.onlineshopping.resource.ItemAttributeValueUpdateResource;
import com.spm.onlineshopping.resource.ItemUpdateResource;
import com.spm.onlineshopping.service.ItemAttributeValueService;
import com.spm.onlineshopping.service.ItemService;
import com.spm.onlineshopping.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class ItemServiceImpl implements ItemService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ItemAttributeValueService itemAttributeValueService;
	
	@Autowired
	private ItemAttributeValueRepository itemAttributeValueRepository;
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}
	
	private int generateId() {
		List<Item> itemList = itemRepository.findAll();
		List<Integer> itemIdList = new ArrayList<>();
		
		for(Item itemObject : itemList) {
			itemIdList.add(itemObject.getId());
		}
		return IdGenerator.generateIDs(itemIdList);	
	}

	@Override
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Override
	public Optional<Item> findById(int id) {
		Optional<Item> isPresentItem = itemRepository.findById(id);
		if(isPresentItem.isPresent()) {
			Item item = isPresentItem.get();
			List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();
			itemAttributeValues = itemAttributeValueRepository.findByItemsId(item.getId());
			item.setItemAttributes(itemAttributeValues);
			return isPresentItem;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Item> findByCode(String code) {
		Optional<Item> isPresentItem = itemRepository.findByCode(code);
		if(isPresentItem.isPresent()) {
			Item item = isPresentItem.get();
			List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();
			itemAttributeValues = itemAttributeValueRepository.findByItemsId(item.getId());
			item.setItemAttributes(itemAttributeValues);
			return isPresentItem;
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<Item> findByStatus(String status) {		
		List<Item> itemss = itemRepository.findByStatus(status);
		List<Item> itemList = new ArrayList<>();
		List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();
		for(Item items : itemss) {
			itemAttributeValues = itemAttributeValueRepository.findByItemsId(items.getId());
			items.setItemAttributes(itemAttributeValues);
			itemList.add(items);
		}
		return itemList;
	}

	@Override
	public List<Item> findByName(String name) {
		List<Item> itemss = itemRepository.findByNameContaining(name);
		List<Item> itemList = new ArrayList<>();
		List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();
		for(Item items : itemss) {
			itemAttributeValues = itemAttributeValueRepository.findByItemsId(items.getId());
			items.setItemAttributes(itemAttributeValues);
			itemList.add(items);
		}
		return itemList;
	}

	@Override
	public List<Item> findByCategoryIdAndStatus(Long categoryId, String status) {
		List<Item> itemss = itemRepository.findByCategorysIdAndStatus(categoryId, status);
		List<Item> itemList = new ArrayList<>();
		List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();
		for(Item items : itemss) {
			itemAttributeValues = itemAttributeValueRepository.findByItemsId(items.getId());
			items.setItemAttributes(itemAttributeValues);
			itemList.add(items);
		}
		return itemList;
	}

	@Override
	public Integer saveItem(ItemAddResource itemAddResource) {
		Item item = new Item();
		
		Optional<Item> isPresentItem = itemRepository.findByCode(itemAddResource.getCode());
        if (isPresentItem.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("code.duplicate"), "message");
		}
		
        Optional<Category> category = categoryRepository.findByIdAndStatus(Integer.parseInt(itemAddResource.getCategoryId()), CommonStatus.ACTIVE.toString());
		if (!category.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("category.invalid-value"), "message");
		} else {
			item.setCategorys(category.get());
		}
		
		Optional<Brand> brand = brandRepository.findByIdAndStatus(Integer.parseInt(itemAddResource.getBrandId()), CommonStatus.ACTIVE.toString());
		if (!brand.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("brand.invalid-value"), "message");
		} else {
			item.setBrands(brand.get());
		}
        
		item.setId(generateId());
		item.setName(itemAddResource.getName());
		item.setCode(itemAddResource.getCode());
		item.setDescription(itemAddResource.getDescription());
		item.setPrice(new BigDecimal(itemAddResource.getPrice()));
		item.setDiscount(new BigDecimal(itemAddResource.getDiscount()));
		item.setImageURL1(itemAddResource.getImageURL1());
		item.setImageURL2(itemAddResource.getImageURL2());
		item.setImageURL3(itemAddResource.getImageURL3());
		item.setImageURL4(itemAddResource.getImageURL4());
		item.setImageURL5(itemAddResource.getImageURL5());
		item.setOutOfStock(itemAddResource.getOutOfStock());
		item.setStatus(itemAddResource.getStatus());
        //category.setCreatedUser(authTokenFilter.getUsername());
		item.setCreatedUser("MKW");
		item.setCreatedDate(formatDate(new Date()));
		
		if(itemAddResource.getItemAttributes() !=null && !itemAddResource.getItemAttributes().isEmpty()) {
			Integer index=0;
			
			for(ItemAttributeValueAddResource itemAttributeValueAddResource : itemAddResource.getItemAttributes()) {
				
				itemAttributeValueService.saveItemAttributeValue(item, itemAttributeValueAddResource, index);	
				index++;
			}			
		}
		
		itemRepository.save(item);
		return item.getId();
	}

	@Override
	public Item updateItem(int id, ItemUpdateResource itemUpdateResource) {
		Optional<Item> isPresentItem = itemRepository.findById(id);
		if (!isPresentItem.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<Item> isPresentItemByCode = itemRepository.findByCodeAndIdNotIn(itemUpdateResource.getCode(), id);
		if (isPresentItemByCode.isPresent())
			throw new ValidateRecordException(environment.getProperty("code.duplicate"), "message");
		
		Item item = isPresentItem.get();
		
		Optional<Category> category = categoryRepository.findByIdAndStatus(Integer.parseInt(itemUpdateResource.getCategoryId()), CommonStatus.ACTIVE.toString());
		if (!category.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("category.invalid-value"), "message");
		} else {
			item.setCategorys(category.get());
		}
		
		Optional<Brand> brand = brandRepository.findByIdAndStatus(Integer.parseInt(itemUpdateResource.getBrandId()), CommonStatus.ACTIVE.toString());
		if (!brand.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("brand.invalid-value"), "message");
		} else {
			item.setBrands(brand.get());
		}
		
		item.setName(itemUpdateResource.getName());
		item.setCode(itemUpdateResource.getCode());
		item.setDescription(itemUpdateResource.getDescription());
		item.setPrice(new BigDecimal(itemUpdateResource.getPrice()));
		item.setDiscount(new BigDecimal(itemUpdateResource.getDiscount()));
		item.setImageURL1(itemUpdateResource.getImageURL1());
		item.setImageURL2(itemUpdateResource.getImageURL2());
		item.setImageURL3(itemUpdateResource.getImageURL3());
		item.setImageURL4(itemUpdateResource.getImageURL4());
		item.setImageURL5(itemUpdateResource.getImageURL5());
		item.setOutOfStock(itemUpdateResource.getOutOfStock());
		item.setStatus(itemUpdateResource.getStatus());
        //category.setModifedUser(authTokenFilter.getUsername());
		item.setModifiedUser("MKW");
		item.setModifiedDate(formatDate(new Date()));
		
		if(itemUpdateResource.getItemAttributes()!=null && !itemUpdateResource.getItemAttributes().isEmpty()) {
			Integer index=0;
			
			for(ItemAttributeValueUpdateResource itemAttributeValueUpdateResource : itemUpdateResource.getItemAttributes()) {
				
				itemAttributeValueService.updateItemAttributeValue(item, itemAttributeValueUpdateResource, index);
				index++;
			}			
		}
		
		itemRepository.save(item);
		return item;
	}

	@Override
	public String deleteItem(int id) {
		Optional<Item> isPresentItem = itemRepository.findById(id);
		if (!isPresentItem.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		itemRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}
	
}
