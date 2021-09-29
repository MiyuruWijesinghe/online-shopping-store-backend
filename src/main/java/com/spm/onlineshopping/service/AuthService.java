package com.spm.onlineshopping.service;

import org.springframework.stereotype.Service;

import com.spm.onlineshopping.model.Users;
import com.spm.onlineshopping.resource.JwtResponseResource;
import com.spm.onlineshopping.resource.LoginRequestResource;
import com.spm.onlineshopping.resource.SignupRequestResource;



@Service
public interface AuthService {

	/**
	 * Authenticate user.
	 *
	 * @param loginRequest the login request
	 * @return the jwt response resource
	 */
	public JwtResponseResource authenticateUser(LoginRequestResource loginRequest);

	/**
	 * Register user.
	 *
	 * @param signUpRequest the sign up request
	 * @return the users
	 */
	public Users registerUser(SignupRequestResource signUpRequest);

}
