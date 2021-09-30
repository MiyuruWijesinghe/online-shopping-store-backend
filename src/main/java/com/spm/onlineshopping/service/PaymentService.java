package com.spm.onlineshopping.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.spm.onlineshopping.resource.PaymentRequestResource;

@Service
public interface PaymentService {

	/**
	 * Save payment details.
	 *
	 * @param dummyPaymentRequestResource the dummy payment request resource
	 * @return the string
	 */
	public String savePaymentDetails(PaymentRequestResource dummyPaymentRequestResource);

}
