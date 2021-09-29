package com.spm.onlineshopping.resource;

import java.util.List;

public class JwtResponseResource {
	private String token;
	private String type = "Bearer";
	private Integer id;
	private String username;
	private String email;
	//private List<String> roles;
	private String role;

//	public JwtResponseResource(String accessToken, Integer id, String username, String email, List<String> roles) {
//		this.token = accessToken;
//		this.id = id;
//		this.username = username;
//		this.email = email;
//		this.roles = roles;
//	}

	
	public JwtResponseResource(String token, Integer id, String username, String email, String role) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}


	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}

//	public List<String> getRoles() {
//		return roles;
//	}
}
