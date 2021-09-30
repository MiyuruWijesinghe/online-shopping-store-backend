package com.spm.onlineshopping.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Orders")
public class Orders {

	@Id
	private Integer id;
	
	private String referenceCode;
	
	private Users users;
	
	private Item items;
	
	private Long quantity;
	
	private BigDecimal deliveryCharge;
	
	private String status;
	
	private String createdDate;
	
	private String modifiedUser;
	
	private String modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Item getItems() {
		return items;
	}

	public void setItems(Item items) {
		this.items = items;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(BigDecimal deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
