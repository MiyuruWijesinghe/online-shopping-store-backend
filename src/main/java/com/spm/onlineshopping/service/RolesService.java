package com.spm.onlineshopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spm.onlineshopping.model.Roles;
import com.spm.onlineshopping.resource.RolesAddResource;
import com.spm.onlineshopping.resource.RolesUpdateResource;


/**
 * Roles Service
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-05-2021   MENUKAJ   IT19004914     Created
 *    
 ********************************************************************************************************
 */

@Service
public interface RolesService {

	public List<Roles> findAll();
	
	public Optional<Roles> findById(int id);
	
	public Optional<Roles> findByName(String name);
	
	public Integer saveRole(RolesAddResource rolesAddResource);
	
	public Roles updateRole(int id, RolesUpdateResource rolesUpdateResource);
	
	public String deleteRole(int id);

}
