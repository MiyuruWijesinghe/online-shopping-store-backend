package com.spm.onlineshopping.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class OrdersAddResource {

	@NotBlank(message = "{common.not-null}")
	private String userName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String deliveryCharge;
	
	@Valid
	private List<OrdersListResource> orders;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(String deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public List<OrdersListResource> getOrders() {
		return orders;
	}

	public void setOrders(List<OrdersListResource> orders) {
		this.orders = orders;
	}
	
}
