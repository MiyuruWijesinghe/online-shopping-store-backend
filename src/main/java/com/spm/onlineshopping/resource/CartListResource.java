package com.spm.onlineshopping.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CartListResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")	
    private String itemId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")	
    private String quantity;
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
}
