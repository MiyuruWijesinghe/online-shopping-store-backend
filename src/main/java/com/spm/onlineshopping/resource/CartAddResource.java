package com.spm.onlineshopping.resource;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CartAddResource {

	@NotBlank(message = "{common.not-null}")
	private String userName;
	
	@Valid
	private List<CartListResource> cartItems;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<CartListResource> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartListResource> cartItems) {
		this.cartItems = cartItems;
	}
	
}
