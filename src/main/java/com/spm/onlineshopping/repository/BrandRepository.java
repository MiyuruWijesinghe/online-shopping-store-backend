package com.spm.onlineshopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.spm.onlineshopping.model.Brand;

@Repository
public interface BrandRepository extends MongoRepository<Brand, Integer> {

	public List<Brand> findByStatus(String status);

	public Optional<Brand> findByName(String name);
	
	public List<Brand> findByNameContaining(String name);

	public Optional<Brand> findByNameAndIdNotIn(String name, int id);

	public Optional<Brand> findByIdAndStatus(int id, String name);
}
