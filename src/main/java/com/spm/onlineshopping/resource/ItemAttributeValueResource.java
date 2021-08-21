package com.spm.onlineshopping.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ItemAttributeValueResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String itemId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String attributeValueId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getAttributeValueId() {
		return attributeValueId;
	}

	public void setAttributeValueId(String attributeValueId) {
		this.attributeValueId = attributeValueId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
