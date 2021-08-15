package com.spm.onlineshopping.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Transient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "Item")
public class Item {

	@Id
	private Integer id;
	
	@JsonIgnore
	private Category categorys;
	
	@Transient
    private Integer categoryId;
	
	@Transient
    private String categoryName;
	
	@JsonIgnore
	private Brand brands;
	
	@Transient
    private Integer brandId;
	
	@Transient
    private String brandName;
	
	private String name;
	
	private String code;
	
	private String description;
	
	private BigDecimal price;
	
	private BigDecimal discount;
	
	private String imageURL1;
	
	private String imageURL2;
	
	private String imageURL3;
	
	private String imageURL4;
	
	private String imageURL5;
	
	private String outOfStock;
	
	private String status;
	
	private String createdUser;
	
	private String createdDate;
	
	private String modifiedUser;
	
	private String modifiedDate;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<ItemAttributeValue> itemAttributes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Category getCategorys() {
		return categorys;
	}

	public void setCategorys(Category categorys) {
		this.categorys = categorys;
	}

	public Integer getCategoryId() {
		if(categorys != null) {
			return categorys.getId();
		} else {
			return null;
		}
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		if(categorys != null) {
			return categorys.getName();
		} else {
			return null;
		}
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Brand getBrands() {
		return brands;
	}

	public void setBrands(Brand brands) {
		this.brands = brands;
	}

	public Integer getBrandId() {
		if(brands != null) {
			return brands.getId();
		} else {
			return null;
		}
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		if(brands != null) {
			return brands.getName();
		} else {
			return null;
		}
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getImageURL1() {
		return imageURL1;
	}

	public void setImageURL1(String imageURL1) {
		this.imageURL1 = imageURL1;
	}

	public String getImageURL2() {
		return imageURL2;
	}

	public void setImageURL2(String imageURL2) {
		this.imageURL2 = imageURL2;
	}

	public String getImageURL3() {
		return imageURL3;
	}

	public void setImageURL3(String imageURL3) {
		this.imageURL3 = imageURL3;
	}

	public String getImageURL4() {
		return imageURL4;
	}

	public void setImageURL4(String imageURL4) {
		this.imageURL4 = imageURL4;
	}

	public String getImageURL5() {
		return imageURL5;
	}

	public void setImageURL5(String imageURL5) {
		this.imageURL5 = imageURL5;
	}

	public String getOutOfStock() {
		return outOfStock;
	}

	public void setOutOfStock(String outOfStock) {
		this.outOfStock = outOfStock;
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

	public List<ItemAttributeValue> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(List<ItemAttributeValue> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
	
}
