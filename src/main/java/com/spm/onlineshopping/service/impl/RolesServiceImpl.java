package com.spm.onlineshopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spm.onlineshopping.enums.CommonStatus;
import com.spm.onlineshopping.exception.NoRecordFoundException;
import com.spm.onlineshopping.exception.ValidateRecordException;
import com.spm.onlineshopping.model.Roles;
import com.spm.onlineshopping.repository.RolesRepository;
import com.spm.onlineshopping.resource.RolesAddResource;
import com.spm.onlineshopping.resource.RolesUpdateResource;
import com.spm.onlineshopping.service.RolesService;
import com.spm.onlineshopping.util.IdGenerator;


/**
 * Roles Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-05-2021   MENUKAJ   IT19004914     Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class RolesServiceImpl implements RolesService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	private int generateId() {
		List<Roles> rolesList = rolesRepository.findAll();
		List<Integer> rolesIdList = new ArrayList<>();
		
		for(Roles rolesObject : rolesList) {
			rolesIdList.add(rolesObject.getId());
		}
		
		return IdGenerator.generateIDs(rolesIdList);	
	}

	@Override
	public List<Roles> findAll() {
		return rolesRepository.findAll();
	}

	@Override
	public Optional<Roles> findById(int id) {
		Optional<Roles> roles = rolesRepository.findById(id);
		if (roles.isPresent()) {
			return Optional.ofNullable(roles.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<Roles> findByName(String name) {
		Optional<Roles> roles = rolesRepository.findByName(name);
		if (roles.isPresent()) {
			return Optional.ofNullable(roles.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Integer saveRole(RolesAddResource rolesAddResource) {
		Roles roles = new Roles();
		
		Optional<Roles> isPresentRole = rolesRepository.findByName("ROLE_" + rolesAddResource.getName());
        if (isPresentRole.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("role.duplicate"), "message");
		}
		
		roles.setId(generateId());
		roles.setName(rolesAddResource.getName());
		roles.setStatus(CommonStatus.ACTIVE.toString());
		rolesRepository.save(roles);
		return roles.getId();
	}

	@Override
	public Roles updateRole(int id, RolesUpdateResource rolesUpdateResource) {
		
		Optional<Roles> isPresentRoles = rolesRepository.findById(id);
		if (!isPresentRoles.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Roles roles = isPresentRoles.get();
		roles.setStatus(rolesUpdateResource.getStatus());
		rolesRepository.save(roles);
		return roles;
	}
	
	@Override
	public String deleteRole(int id) {
		Optional<Roles> isPresentRoles = rolesRepository.findById(id);
		if (!isPresentRoles.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		rolesRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

}