package com.spm.onlineshopping.resource;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LoginRequestResource {
	
	@NotBlank(message = "{common.not-null}")
	private String userName;

	@NotBlank(message = "{common.not-null}")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
