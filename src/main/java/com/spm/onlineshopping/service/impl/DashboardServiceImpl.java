package com.spm.onlineshopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.spm.onlineshopping.repository.BrandRepository;
import com.spm.onlineshopping.repository.CategoryRepository;
import com.spm.onlineshopping.repository.ItemRepository;
import com.spm.onlineshopping.resource.AdminDashboardResponse;
import com.spm.onlineshopping.service.DashboardService;


@Component
@Transactional(rollbackFor=Exception.class)
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Override
	public AdminDashboardResponse getAdminDashboardDetails() {
		
		AdminDashboardResponse adminDashboardResponse = new AdminDashboardResponse();
		
		Long totalItems = itemRepository.count();
		Long totalCategories = categoryRepository.count();
		Long totalBrands = brandRepository.count();
		Long totalAdmins = Long.valueOf(0);
		Long totalSellers = Long.valueOf(0);
		Long totalBuyers = Long.valueOf(0);
		Long totalOrders = Long.valueOf(0);
		
		adminDashboardResponse.setTotalItems(totalItems.toString());
		adminDashboardResponse.setTotalCategories(totalCategories.toString());
		adminDashboardResponse.setTotalBrands(totalBrands.toString());
		adminDashboardResponse.setTotalAdmins(totalAdmins.toString());
		adminDashboardResponse.setTotalSellers(totalSellers.toString());
		adminDashboardResponse.setTotalBuyers(totalBuyers.toString());
		adminDashboardResponse.setTotalOrders(totalOrders.toString());
		
		return adminDashboardResponse;
	}

	
}
