package com.spm.onlineshopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.spm.onlineshopping.model.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, Integer> {
	
	public List<Cart> findByUsersUsernameAndStatus(String username, String status);
	
	public List<Cart> findByStatus(String status);
	
	public Optional<Cart> findByUsersUsernameAndItemsIdAndStatus(String username, int itemId, String status);

	public Boolean existsByUsersUsernameAndItemsIdAndStatus(String username, int itemId, String status);
	
	public void deleteAllByUsersUsernameAndStatus(String username, String status);
}
