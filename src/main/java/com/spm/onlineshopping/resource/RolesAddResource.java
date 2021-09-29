package com.spm.onlineshopping.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RolesAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ADMIN|BUYER|SELLER", message = "{roles.pattern}")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}