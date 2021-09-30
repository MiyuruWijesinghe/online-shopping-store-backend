package com.spm.onlineshopping.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spm.onlineshopping.model.Users;
import com.spm.onlineshopping.resource.JwtResponseResource;
import com.spm.onlineshopping.resource.LoginRequestResource;
import com.spm.onlineshopping.resource.SignupRequestResource;



@Service
public interface AuthService {

	public JwtResponseResource authenticateUser(LoginRequestResource loginRequest);

	public Users registerUser(SignupRequestResource signUpRequest);
	
	public Optional<Users> findByUserName(String username);

}
