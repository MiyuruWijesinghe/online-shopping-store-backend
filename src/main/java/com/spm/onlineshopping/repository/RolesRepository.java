package com.spm.onlineshopping.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spm.onlineshopping.model.Roles;


/**
 * Roles Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-05-2021   MiyuruW   IT19020990     Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface RolesRepository extends MongoRepository<Roles, Integer> {

	public Optional<Roles> findByIdAndStatus(int id, String status);

	public Optional<Roles> findByName(String name);

}
