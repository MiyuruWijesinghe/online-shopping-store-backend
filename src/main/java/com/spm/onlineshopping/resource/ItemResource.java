package com.spm.onlineshopping.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ItemResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")	
    private String categoryId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String brandId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@Size(max = 255, message = "{description.size}")
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String price;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String discount;
	
	@NotBlank(message = "{common.not-null}")
	private String imageURL1;
	
	@NotBlank(message = "{common.not-null}")
	private String imageURL2;
	
	@NotBlank(message = "{common.not-null}")
	private String imageURL3;
	
	@NotBlank(message = "{common.not-null}")
	private String imageURL4;
	
	@NotBlank(message = "{common.not-null}")
	private String imageURL5;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|YER|NO",message="{indicator.pattern}")
	private String outOfStock;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
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
}
