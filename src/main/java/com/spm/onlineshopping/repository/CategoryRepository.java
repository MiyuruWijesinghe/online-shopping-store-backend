package com.spm.onlineshopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.spm.onlineshopping.model.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, Integer> {

	public List<Category> findByStatus(String status);

	public Optional<Category> findByName(String name);
	
	public List<Category> findByNameContaining(String name);

	public Optional<Category> findByNameAndIdNotIn(String name, int id);

	public Optional<Category> findByIdAndStatus(int id, String name);
}
