package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spm.onlineshopping.model.Orders;
import com.spm.onlineshopping.resource.OrdersAddResource;
import com.spm.onlineshopping.resource.OrdersUpdateResource;


@Service
public interface OrdersService {

	public List<Orders> findAll();
	
	public Optional<Orders> findById(int id);
	
	public List<Orders> findByStatus(String status);
	
	public List<Orders> findByReferenceCode(String referenceCode);
	
	public List<Orders> findByUsersUsername(String username);
	
	public Optional<Orders> findByReferenceCodeAndUsersUsernameAndItemsIdAndStatus(String referenceCode, String username, int itemId, String status);
	
	public String saveOrders(OrdersAddResource ordersAddResource);
	
	public String updateOrders(String referenceCode, OrdersUpdateResource ordersUpdateResource);
	
	public String deleteOrders(int id);
	
}
