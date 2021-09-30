package com.spm.onlineshopping.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Payment")
public class Payment {
	
	@Id
	private Integer id;
	
	private String paymentRefCode;
	
	private String orderRefCode;
	
	private String status;
	
	private BigDecimal amount;
	
	private String createdDate;
	
	private String createdUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaymentRefCode() {
		return paymentRefCode;
	}

	public void setPaymentRefCode(String paymentRefCode) {
		this.paymentRefCode = paymentRefCode;
	}

	public String getOrderRefCode() {
		return orderRefCode;
	}

	public void setOrderRefCode(String orderRefCode) {
		this.orderRefCode = orderRefCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

}
