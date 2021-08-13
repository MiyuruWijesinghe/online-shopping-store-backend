package com.spm.onlineshopping.exception;

import com.spm.onlineshopping.enums.ServiceEntity;

public class InvalidServiceIdException extends RuntimeException {

	private static final long serialVersionUID = -5687046029326193822L;
	
	private final ServiceEntity serviceEntity;
	
	public InvalidServiceIdException(String exception,ServiceEntity serviceEntity) {
		super(exception);
		this.serviceEntity=serviceEntity;
	}
	
	public ServiceEntity getServiceEntity() {
		return serviceEntity;
	}
}
