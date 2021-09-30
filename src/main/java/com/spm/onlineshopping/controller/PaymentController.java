package com.spm.onlineshopping.controller;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spm.onlineshopping.resource.PaymentRequestResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.model.Brand;
import com.spm.onlineshopping.model.Payment;
import com.spm.onlineshopping.resource.MessageResponseResource;
import com.spm.onlineshopping.service.PaymentService;

/**
 * The Class PaymentController.
 */
@RestController
@RequestMapping(value = "/payment")
@CrossOrigin(origins = "*")
public class PaymentController {
	

	@Autowired
	private Environment environment;
	
	@Autowired
	private PaymentService paymentService;
	

	/**
	 * Pay.
	 *
	 * @param dummyPaymentRequestResource the dummy payment request resource
	 * @return the response entity
	 */
	@PostMapping("/pay")
	public ResponseEntity<Object> pay(@Valid @RequestBody PaymentRequestResource dummyPaymentRequestResource){
		String refNo = paymentService.savePaymentDetails(dummyPaymentRequestResource);
		MessageResponseResource responseMessage = new MessageResponseResource(environment.getProperty("payment.sucess"));
		responseMessage.setRefrenceNo(refNo);
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllPayment() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Payment> payments = paymentService.findAll();
		if (!payments.isEmpty()) {
			return new ResponseEntity<>((Collection<Payment>) payments, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
