package com.spm.onlineshopping.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.spm.onlineshopping.model.Payment;
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

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Payment> findAll();

}
