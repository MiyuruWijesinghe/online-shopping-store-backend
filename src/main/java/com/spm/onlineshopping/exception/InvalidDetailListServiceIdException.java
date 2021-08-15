package com.spm.onlineshopping.exception;

import com.spm.onlineshopping.enums.ServiceEntity;
import com.spm.onlineshopping.enums.TransferType;

public class InvalidDetailListServiceIdException extends RuntimeException {
	private final ServiceEntity serviceEntity;
	private final TransferType transferType;
	private final Integer index;
	public InvalidDetailListServiceIdException(String exception, ServiceEntity serviceEntity, TransferType transferType, Integer index) {
		super(exception);
		this.serviceEntity=serviceEntity;
		this.transferType=transferType;
		this.index=index;
	}
	public ServiceEntity getServiceEntity() {
		return serviceEntity;
	}
	public TransferType getTransferType() {
		return transferType;
	}
	public Integer getIndex() {
		return index;
	}
}
