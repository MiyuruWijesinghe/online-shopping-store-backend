package com.spm.onlineshopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.spm.onlineshopping.model.Attribute;

@Repository
public interface AttributeRepository extends MongoRepository<Attribute, Integer> {

	public List<Attribute> findByStatus(String status);

	public Optional<Attribute> findByName(String name);
	
	public List<Attribute> findByNameContaining(String name);

	public Optional<Attribute> findByNameAndIdNotIn(String name, int id);

	public Optional<Attribute> findByIdAndStatus(int id, String name);
	
}
