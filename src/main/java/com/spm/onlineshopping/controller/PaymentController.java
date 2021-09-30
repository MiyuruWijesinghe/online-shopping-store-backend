package com.spm.onlineshopping.controller;

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
	 * Gets the delevery status by id.
	 *
	 * @param cardNO the card NO
	 * @return the delevery status by id
	 */
	@GetMapping(value = "/validate/{cardNO}")
	public ResponseEntity<Object> getDeleveryStatusById(@PathVariable(value = "id", required = true) Long cardNO){
		MessageResponseResource responseMessage = new MessageResponseResource();
		String[] names = { "VALID", "INVALID" };
		String name = names[(int) (Math.random() * names.length)];
		responseMessage.setStatus(name);
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);
	}
	

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

}
