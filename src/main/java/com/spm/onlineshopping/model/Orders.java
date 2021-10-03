package com.spm.onlineshopping.model;

import java.math.BigDecimal;

import javax.persistence.Transient;

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
	
	private BigDecimal price;
	
	private BigDecimal discount;
	
	private BigDecimal netAmount;
	
	private BigDecimal subTotal;
	
	private BigDecimal deliveryCharge;
	
	private String status;
	
	private String createdDate;
	
	private String modifiedUser;
	
	private String modifiedDate;
	
	@Transient
	private String categoryName;
	
	@Transient
	private String brandName;
	
	@Transient
	private String itemName;
	
	@Transient
	private BigDecimal itemPrice;
	
	@Transient
	private BigDecimal itemDiscount;
	
	@Transient
	private String itemImage;
	

	public String getCategoryName() {
		if(items != null) {
			return items.getCategoryName();
		} else {
			return null;
		}
	}

	public String getBrandName() {
		if(items != null) {
			return items.getBrandName();
		} else {
			return null;
		}
	}

	public String getItemName() {
		if(items != null) {
			return items.getName();
		} else {
			return null;
		}
	}

	public BigDecimal getItemPrice() {
		if(items != null) {
			return items.getPrice();
		} else {
			return null;
		}
	}

	public BigDecimal getItemDiscount() {
		if(items != null) {
			return items.getDiscount();
		} else {
			return null;
		}
	}

	public String getItemImage() {
		if(items != null) {
			return items.getImageURL1();
		} else {
			return null;
		}
	}

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
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
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
