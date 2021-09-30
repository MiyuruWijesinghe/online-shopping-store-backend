package com.spm.onlineshopping.controller;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spm.onlineshopping.model.Users;
import com.spm.onlineshopping.resource.JwtResponseResource;
import com.spm.onlineshopping.resource.LoginRequestResource;
import com.spm.onlineshopping.resource.MessageResponseResource;
import com.spm.onlineshopping.resource.SignupRequestResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.service.AuthService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	AuthService authService;
	
	@GetMapping(value = "/buyer/{username}")
	public ResponseEntity<Object> getBuyerByUserName(@PathVariable(value = "username", required = true) String username) {
		SuccessAndErrorDetailsResource responsemessage = new SuccessAndErrorDetailsResource();
		Optional<Users> isPresentUser = authService.findByUserName(username);
		if(isPresentUser.isPresent()) {
			return new ResponseEntity<>(isPresentUser, HttpStatus.OK);
		} else {
			responsemessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responsemessage, HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestResource loginRequest) {
		JwtResponseResource jwtResponseResource = authService.authenticateUser(loginRequest);
		return new ResponseEntity<>(jwtResponseResource,HttpStatus.CREATED);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestResource signUpRequest) {
		
		Users user =  authService.registerUser(signUpRequest);
		MessageResponseResource responseMessage = new MessageResponseResource(environment.getProperty("registered.success"), Long.toString(user.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
}
