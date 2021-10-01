package com.spm.onlineshopping.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spm.onlineshopping.enums.CommonStatus;
import com.spm.onlineshopping.exception.NoRecordFoundException;
import com.spm.onlineshopping.exception.ValidateRecordException;
import com.spm.onlineshopping.model.Roles;
import com.spm.onlineshopping.model.Users;
import com.spm.onlineshopping.repository.RolesRepository;
import com.spm.onlineshopping.repository.UserRepository;
import com.spm.onlineshopping.resource.BuyerUpdateResource;
import com.spm.onlineshopping.resource.JwtResponseResource;
import com.spm.onlineshopping.resource.LoginRequestResource;
import com.spm.onlineshopping.resource.SignupRequestResource;
import com.spm.onlineshopping.security.jwt.JwtUtils;
import com.spm.onlineshopping.service.AuthService;
import com.spm.onlineshopping.util.IdGenerator;


@Component
@Transactional(rollbackFor=Exception.class)
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RolesRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	

	@Autowired
	JwtUtils jwtUtils;

	@Override
	public JwtResponseResource authenticateUser(LoginRequestResource loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		String role = roles.get(0);
		return new JwtResponseResource(jwt, 
				 userDetails.getId(), 
				 userDetails.getUsername(), 
				 userDetails.getEmail(), 
				 role);
	}

	@Override
	public Users registerUser(SignupRequestResource signUpRequest) {
		Set<Roles> roles = new HashSet<>();
		
		Users user = new Users();
		
		Optional<Users> isPresentUser = userRepository.findByUsername(signUpRequest.getUserName());
        if (isPresentUser.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("user.duplicate"), "message");
		}
        
        Optional<Roles> role = roleRepository.findByName(signUpRequest.getRoleName());
		if (!role.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("role.invalid-value"), "message");
		} else {
			roles.add(role.get());
		}
		
		
		user.setId(generateId());
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setDob(signUpRequest.getDob());
		user.setNic(signUpRequest.getNic());
		user.setUsername(signUpRequest.getUserName());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setAddressLine1(signUpRequest.getAddressLine1());
		user.setAddressLine2(signUpRequest.getAddressLine2());
		user.setAddressLine3(signUpRequest.getAddressLine3());
		user.setEmail(signUpRequest.getEmail());
		user.setPhoneNumber(signUpRequest.getPhoneNumber());
		user.setStatus(CommonStatus.ACTIVE.toString());
		user.setRoles(roles);
		userRepository.save(user);
		return user;
		
	}
	
	@Override
	public Optional<Users> findByUserName(String username) {
		Optional<Users> user = userRepository.findByUsername(username);
		if(user.isPresent()) {
			return Optional.ofNullable(user.get());
		} else {
			return Optional.empty();
		}
	}
		
	@Override
	public Users updateBuyer(String username, BuyerUpdateResource buyerUpdateResource) {
		Optional<Users> isPresentBuyer = userRepository.findByUsername(username);
		if(!isPresentBuyer.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Users user = isPresentBuyer.get();
		user.setFirstName(buyerUpdateResource.getFirstName());
		user.setLastName(buyerUpdateResource.getLastName());
		user.setDob(buyerUpdateResource.getDob());
		user.setNic(buyerUpdateResource.getNic());
		user.setUsername(buyerUpdateResource.getUsername());
		user.setUserImage(buyerUpdateResource.getUserImage());
		user.setAddressLine1(buyerUpdateResource.getAddressLine1());
		user.setAddressLine2(buyerUpdateResource.getAddressLine2());
		user.setAddressLine3(buyerUpdateResource.getAddressLine3());
		user.setEmail(buyerUpdateResource.getEmail());
		user.setPhoneNumber(buyerUpdateResource.getPhoneNumber());
		user.setStatus(CommonStatus.ACTIVE.toString());
		userRepository.save(user);
		return user;
	}
		
		
		
		
		
		
//		if (userRepository.existsByUsername(signUpRequest.getUsername()))
//			throw new ValidateRecordException(environment.getProperty("user.username-exists"), "message");
//
//		if (userRepository.existsByEmail(signUpRequest.getEmail()))
//			throw new ValidateRecordException(environment.getProperty("user.email-exists"), "message");
//
//		Users user = new Users(signUpRequest.getUsername(), 
//							 signUpRequest.getEmail(),
//							 encoder.encode(signUpRequest.getPassword()));
//
//		String strRoles = signUpRequest.getRoleName();
//		Set<Roles> roles = new HashSet<>();
//		
//		Roles userRole = roleRepository.findByName(signUpRequest.getRoleName())
//				.orElseThrow(() -> new NoRecordFoundException(environment.getProperty("role-not-found")));
//		roles.add(userRole);
//
////		if (strRoles == null) {
////			Roles userRole = roleRepository.findByName("ROLE_USER")
////					.orElseThrow(() -> new NoRecordFoundException(environment.getProperty("role-not-found")));
////			roles.add(userRole);
////		} else {
////			strRoles.forEach(role -> {
////				switch (role) {
////				case "admin":
////					Roles adminRole = roleRepository.findByName("ROLE_ADMIN")
////							.orElseThrow(() -> new NoRecordFoundException(environment.getProperty("role-not-found")));
////					roles.add(adminRole);
////
////					break;
////				default:
////					Roles userRole = roleRepository.findByName("ROLE_USER")
////							.orElseThrow(() -> new NoRecordFoundException(environment.getProperty("role-not-found")));
////					roles.add(userRole);
////				}
////			});
////		}
//		user.setId(generateId());
//		user.setRoles(roles);
//		return userRepository.save(user);
//	}
	
	private int generateId() {
		List<Users> userList = userRepository.findAll();
		List<Integer> userIdList = new ArrayList<>();
		
		for(Users userObject : userList) {
			userIdList.add(userObject.getId());
		}
		
		return IdGenerator.generateIDs(userIdList);	
	}
}
