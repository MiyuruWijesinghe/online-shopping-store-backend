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
import com.spm.onlineshopping.model.Item;
import com.spm.onlineshopping.model.Orders;
import com.spm.onlineshopping.model.Users;
import com.spm.onlineshopping.repository.CartRepository;
import com.spm.onlineshopping.repository.ItemRepository;
import com.spm.onlineshopping.repository.OrdersRepository;
import com.spm.onlineshopping.repository.UserRepository;
import com.spm.onlineshopping.resource.OrdersAddResource;
import com.spm.onlineshopping.resource.OrdersListResource;
import com.spm.onlineshopping.resource.OrdersUpdateResource;
import com.spm.onlineshopping.security.jwt.AuthTokenFilter;
import com.spm.onlineshopping.service.OrdersService;
import com.spm.onlineshopping.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private AuthTokenFilter authTokenFilter;
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}
	
	private int generateId() {
		List<Orders> ordersList = ordersRepository.findAll();
		List<Integer> ordersIdList = new ArrayList<>();
		
		for(Orders ordersObject : ordersList) {
			ordersIdList.add(ordersObject.getId());
		}
		return IdGenerator.generateIDs(ordersIdList);	
	}
	
	private int generateRefNo() {
		List<Orders> ordersList = ordersRepository.findAll();
		List<Integer> ordersIdList = new ArrayList<>();
		
		for(Orders ordersObject : ordersList) {
			ordersIdList.add(ordersObject.getId());
		}
		return IdGenerator.generateIDs(ordersIdList);	
	}
	
	@Override
	public List<Orders> findAll() {
		return ordersRepository.findAll();
	}

	@Override
	public Optional<Orders> findById(int id) {
		Optional<Orders> orders = ordersRepository.findById(id);
		if (orders.isPresent()) {
			return Optional.ofNullable(orders.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Orders> findByStatus(String status) {
		return ordersRepository.findByStatus(status);
	}

	@Override
	public List<Orders> findByReferenceCode(String referenceCode) {
		return ordersRepository.findByReferenceCode(referenceCode);
	}

	@Override
	public List<Orders> findByUsersUsername(String username) {
		return ordersRepository.findByUsersUsername(username);
	}

	@Override
	public Optional<Orders> findByReferenceCodeAndUsersUsernameAndItemsIdAndStatus(String referenceCode, String username, int itemId, String status) {
		Optional<Orders> orders = ordersRepository.findByReferenceCodeAndUsersUsernameAndItemsIdAndStatus(referenceCode, username, itemId, status);
		if (orders.isPresent()) {
			return Optional.ofNullable(orders.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public String saveOrders(OrdersAddResource ordersAddResource) {
		
		Orders orders = new Orders();
		
		String refCode = "RF"+generateRefNo();
		String username = ordersAddResource.getUserName();
		String deliveryCharge = ordersAddResource.getDeliveryCharge();
		
		for(OrdersListResource orderObject : ordersAddResource.getOrders()) {
			orders.setId(generateId());
			orders.setStatus(CommonStatus.ACTIVE.toString());
			orders.setReferenceCode(refCode);
			
			Optional<Users> users = userRepository.findByUsername(username);
			if (users.isPresent()) {
				orders.setUsers(users.get());
			}
			
			Optional<Item> item = itemRepository.findByIdAndStatus(Integer.parseInt(orderObject.getItemId()), CommonStatus.ACTIVE.toString());
			if (item.isPresent()) {
				orders.setItems(item.get());
			}
			
			orders.setQuantity(Long.parseLong(orderObject.getQuantity()));
			orders.setDeliveryCharge(new BigDecimal(deliveryCharge));
			orders.setCreatedDate(formatDate(new Date()));
			ordersRepository.save(orders);
		}
		
		//Delete from cart
		cartRepository.deleteAllByUsersUsernameAndStatus(username, CommonStatus.ACTIVE.toString());
		
		return refCode;
	}

	@Override
	public String updateOrders(String referenceCode, OrdersUpdateResource ordersUpdateResource) {
		
		List<Orders> ordersList = ordersRepository.findByReferenceCode(referenceCode);
		
		for(Orders orders : ordersList) {
			orders.setStatus(ordersUpdateResource.getStatus());
			orders.setModifiedDate(formatDate(new Date()));
			orders.setModifiedUser(authTokenFilter.getUsername());
			ordersRepository.save(orders);
		}
		
		return null;
	}

	@Override
	public String deleteOrders(int id) {
		Optional<Orders> isPresentOrders = ordersRepository.findById(id);
		if (!isPresentOrders.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		ordersRepository.deleteById(id);
		return environment.getProperty("order-item.deleted");
	}
	
}
