package com.spm.onlineshopping.resource;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class OrdersAddResource {

	@Valid
	private List<OrdersListResource> orders;

	public List<OrdersListResource> getOrders() {
		return orders;
	}

	public void setOrders(List<OrdersListResource> orders) {
		this.orders = orders;
	}
	
}
