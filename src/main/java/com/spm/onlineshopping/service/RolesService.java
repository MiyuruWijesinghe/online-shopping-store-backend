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

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Roles> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	public Optional<Roles> findById(int id);
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the optional
	 */
	public Optional<Roles> findByName(String name);
	
	/**
	 * Save role.
	 *
	 * @param rolesAddResource the roles add resource
	 * @return the integer
	 */
	public Integer saveRole(RolesAddResource rolesAddResource);
	
	/**
	 * Update role.
	 *
	 * @param id the id
	 * @param rolesUpdateResource the roles update resource
	 * @return the roles
	 */
	public Roles updateRole(int id, RolesUpdateResource rolesUpdateResource);
	
	/**
	 * Delete role.
	 *
	 * @param id the id
	 * @return the string
	 */
	public String deleteRole(int id);

}
