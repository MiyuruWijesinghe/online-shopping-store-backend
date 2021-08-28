package com.spm.onlineshopping.service;

import org.springframework.stereotype.Service;
import com.spm.onlineshopping.resource.AdminDashboardResponse;


@Service
public interface DashboardService {

	public AdminDashboardResponse getAdminDashboardDetails();
	
}
