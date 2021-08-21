package com.spm.onlineshopping.model;

import javax.persistence.Transient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "ItemAttributeValue")
public class ItemAttributeValue {

	@Id
	private Integer id;
	
	@JsonIgnore
	private Item items;
	
	@Transient
    private Integer itemId;
	
	@Transient
    private String itemName;
	
	@JsonIgnore
	private AttributeValue attributeValues;
	
	@Transient
    private String attributeName;
	
	@Transient
    private Integer attributeValueId;
	
	@Transient
    private String attributeValue;
	
	private String status;
	
	private String createdUser;
	
	private String createdDate;
	
	private String modifiedUser;
	
	private String modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Item getItems() {
		return items;
	}

	public void setItems(Item items) {
		this.items = items;
	}

	public Integer getItemId() {
		if(items != null) {
			return items.getId();
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

	public AttributeValue getAttributeValues() {
		return attributeValues;
	}

	public void setAttributeValues(AttributeValue attributeValues) {
		this.attributeValues = attributeValues;
	}

	public Integer getAttributeValueId() {
		if(attributeValues != null) {
			return attributeValues.getId();
		} else {
			return null;
		}
	}

	public String getAttributeName() {
		if(attributeValues != null) {
			return attributeValues.getAttributeName();
		} else {
			return null;
		}
	}

	public String getAttributeValue() {
		if(attributeValues != null) {
			return attributeValues.getName();
		} else {
			return null;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
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
