package com.spm.onlineshopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.spm.onlineshopping.model.AttributeValue;

@Repository
public interface AttributeValueRepository extends MongoRepository<AttributeValue, Integer> {

	public List<AttributeValue> findByStatus(String status);

	public Optional<AttributeValue> findByAttributesIdAndName(int attributeId, String name);
	
	public List<AttributeValue> findByNameContaining(String name);

	public Optional<AttributeValue> findByAttributesIdAndNameAndIdNotIn(int attributeId, String name, int id);

	public Optional<AttributeValue> findByIdAndStatus(int id, String name);
	
	public List<AttributeValue> findByAttributesId(int attributeId);
}
