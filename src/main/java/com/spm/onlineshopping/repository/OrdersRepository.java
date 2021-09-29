package com.spm.onlineshopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.spm.onlineshopping.model.Orders;

@Repository
public interface OrdersRepository extends MongoRepository<Orders, Integer> {

	public List<Orders> findByReferenceCode(String referenceCode);
	
	public List<Orders> findByUsersUsername(String username);
	
	public List<Orders> findByStatus(String status);
	
	public Optional<Orders> findByReferenceCodeAndUsersUsernameAndItemsIdAndStatus(String referenceCode, String username, int itemId, String status);

	public Boolean existsByReferenceCodeAndUsersUsernameAndItemsIdAndStatus(String referenceCode, String username, int itemId, String status);
}
