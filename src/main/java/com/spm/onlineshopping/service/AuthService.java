package com.spm.onlineshopping.service;

import org.springframework.stereotype.Service;

import com.spm.onlineshopping.model.Users;
import com.spm.onlineshopping.resource.JwtResponseResource;
import com.spm.onlineshopping.resource.LoginRequestResource;
import com.spm.onlineshopping.resource.SignupRequestResource;



@Service
public interface AuthService {

	public JwtResponseResource authenticateUser(LoginRequestResource loginRequest);

	public Users registerUser(SignupRequestResource signUpRequest);

}
