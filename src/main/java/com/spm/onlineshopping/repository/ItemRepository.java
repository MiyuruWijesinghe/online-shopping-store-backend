package com.spm.onlineshopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spm.onlineshopping.model.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, Integer> {

	public Optional<Item> findByCode(String code);
	
	public List<Item> findByStatus(String status);
	
	public List<Item> findByNameContaining(String name);

	public List<Item> findByCategorysIdAndStatus(Long categoryId, String status);
	
	public Optional<Item> findByCodeAndIdNotIn(String code, int id);

	public Optional<Item> findByIdAndStatus(int id, String name);

}
