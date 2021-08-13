package com.spm.onlineshopping.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MessageResponseResource {

	@JsonProperty("message")
	private String message;

	@JsonProperty("details")
	private String details;

	@JsonProperty("value")
	private String value;

	@JsonProperty("response")
	private Object response;

	@JsonProperty("refrenceNo")
	private String refrenceNo;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("deliveryFee")
	private String deliveryFee;
	
	@JsonProperty("bank")
	private String bank;
	
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefrenceNo() {
		return refrenceNo;
	}

	public void setRefrenceNo(String refrenceNo) {
		this.refrenceNo = refrenceNo;
	}

	public MessageResponseResource() {
		super();
	}
	
	public MessageResponseResource(String message) {
		super();
		this.message = message;
	}

	public MessageResponseResource(String message, String value) {
		super();
		this.message = message;
		this.value = value;
	}
	
	public MessageResponseResource(String message, Object response) {
		super();
		this.message = message;
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
