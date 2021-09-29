package com.spm.onlineshopping.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spm.onlineshopping.model.Users;

@Repository
public interface UserRepository extends MongoRepository<Users, Integer> {
	
	public Optional<Users> findByUsername(String username);

	//Boolean existsByUsername(String username);

	//Boolean existsByEmail(String email);

}
