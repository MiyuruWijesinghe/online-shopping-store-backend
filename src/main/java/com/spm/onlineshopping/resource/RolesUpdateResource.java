package com.spm.onlineshopping.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RolesUpdateResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}