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
import com.spm.onlineshopping.repository.BrandRepository;
import com.spm.onlineshopping.repository.CategoryRepository;
import com.spm.onlineshopping.repository.ItemRepository;
import com.spm.onlineshopping.resource.ItemResource;
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
			return Optional.ofNullable(isPresentItem.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Item> findByCode(String code) {
		Optional<Item> isPresentItem = itemRepository.findByCode(code);
		if(isPresentItem.isPresent()) {
			return Optional.ofNullable(isPresentItem.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<Item> findByStatus(String status) {		
		return itemRepository.findByStatus(status);
	}

	@Override
	public List<Item> findByName(String name) {
		return itemRepository.findByNameContaining(name);
	}

	@Override
	public List<Item> findByCategoryIdAndStatus(int categoryId, String status) {
		return itemRepository.findByCategorysIdAndStatus(categoryId, status);
	}

	@Override
	public Integer saveItem(ItemResource itemResource) {
		Item item = new Item();
		
		Optional<Item> isPresentItem = itemRepository.findByCode(itemResource.getCode());
        if (isPresentItem.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("code.duplicate"), "message");
		}
		
        Optional<Category> category = categoryRepository.findByIdAndStatus(Integer.parseInt(itemResource.getCategoryId()), CommonStatus.ACTIVE.toString());
		if (!category.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("category.invalid-value"), "message");
		} else {
			item.setCategorys(category.get());
		}
		
		Optional<Brand> brand = brandRepository.findByIdAndStatus(Integer.parseInt(itemResource.getBrandId()), CommonStatus.ACTIVE.toString());
		if (!brand.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("brand.invalid-value"), "message");
		} else {
			item.setBrands(brand.get());
		}
        
		item.setId(generateId());
		item.setName(itemResource.getName());
		item.setCode(itemResource.getCode());
		item.setDescription(itemResource.getDescription());
		item.setPrice(new BigDecimal(itemResource.getPrice()));
		item.setDiscount(new BigDecimal(itemResource.getDiscount()));
		item.setImageURL1(itemResource.getImageURL1());
		item.setImageURL2(itemResource.getImageURL2());
		item.setImageURL3(itemResource.getImageURL3());
		item.setImageURL4(itemResource.getImageURL4());
		item.setImageURL5(itemResource.getImageURL5());
		item.setOutOfStock(itemResource.getOutOfStock());
		item.setStatus(itemResource.getStatus());
        //category.setCreatedUser(authTokenFilter.getUsername());
		item.setCreatedUser("MKW");
		item.setCreatedDate(formatDate(new Date()));
		itemRepository.save(item);
		return item.getId();
	}

	@Override
	public Item updateItem(int id, ItemResource itemResource) {
		Optional<Item> isPresentItem = itemRepository.findById(id);
		if (!isPresentItem.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<Item> isPresentItemByCode = itemRepository.findByCodeAndIdNotIn(itemResource.getCode(), id);
		if (isPresentItemByCode.isPresent())
			throw new ValidateRecordException(environment.getProperty("code.duplicate"), "message");
		
		Item item = isPresentItem.get();
		
		Optional<Category> category = categoryRepository.findByIdAndStatus(Integer.parseInt(itemResource.getCategoryId()), CommonStatus.ACTIVE.toString());
		if (!category.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("category.invalid-value"), "message");
		} else {
			item.setCategorys(category.get());
		}
		
		Optional<Brand> brand = brandRepository.findByIdAndStatus(Integer.parseInt(itemResource.getBrandId()), CommonStatus.ACTIVE.toString());
		if (!brand.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("brand.invalid-value"), "message");
		} else {
			item.setBrands(brand.get());
		}
		
		item.setName(itemResource.getName());
		item.setCode(itemResource.getCode());
		item.setDescription(itemResource.getDescription());
		item.setPrice(new BigDecimal(itemResource.getPrice()));
		item.setDiscount(new BigDecimal(itemResource.getDiscount()));
		item.setImageURL1(itemResource.getImageURL1());
		item.setImageURL2(itemResource.getImageURL2());
		item.setImageURL3(itemResource.getImageURL3());
		item.setImageURL4(itemResource.getImageURL4());
		item.setImageURL5(itemResource.getImageURL5());
		item.setOutOfStock(itemResource.getOutOfStock());
		item.setStatus(itemResource.getStatus());
        //category.setModifedUser(authTokenFilter.getUsername());
		item.setModifiedUser("MKW");
		item.setModifiedDate(formatDate(new Date()));
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
