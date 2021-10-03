package com.spm.onlineshopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spm.onlineshopping.model.ItemAttributeValue;

@Repository
public interface ItemAttributeValueRepository extends MongoRepository<ItemAttributeValue, Integer> {

	public Optional<ItemAttributeValue> findByItemsIdAndAttributeValuesId(int itemId, int attributeValueId);

	public Optional<ItemAttributeValue> findByItemsIdAndAttributeValuesIdAndIdNotIn(int itemId, int attributeValueId, int id);

	public List<ItemAttributeValue> findByItemsId(int itemId);
	
	public List<ItemAttributeValue> findByStatus(String status);

	public List<ItemAttributeValue> findByItemsIdAndStatus(int itemId, String status);
}
