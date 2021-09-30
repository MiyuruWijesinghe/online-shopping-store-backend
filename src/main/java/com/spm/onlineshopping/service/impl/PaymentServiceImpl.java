package com.spm.onlineshopping.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spm.onlineshopping.enums.PaymentStatus;
import com.spm.onlineshopping.model.Payment;
import com.spm.onlineshopping.repository.PaymentRepository;
import com.spm.onlineshopping.resource.PaymentRequestResource;
import com.spm.onlineshopping.security.jwt.AuthTokenFilter;
import com.spm.onlineshopping.service.PaymentService;
import com.spm.onlineshopping.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private AuthTokenFilter authTokenFilter;

	@Override
	public String savePaymentDetails(PaymentRequestResource paymentRequestResource) {
		String payRefCode = externalPaymentServiceCall(paymentRequestResource.getCardNumber(), 
				Integer.parseInt(paymentRequestResource.getCvCode()), 
				Integer.parseInt(paymentRequestResource.getMonth()),
				Integer.parseInt(paymentRequestResource.getYear()), 
				new BigDecimal(paymentRequestResource.getAmount()));
		
		savePayment(paymentRequestResource, payRefCode);
		
		return payRefCode;
	}
	
	private void savePayment(PaymentRequestResource paymentRequestResource, String payRefCode) {
		
		Payment payment = new Payment();
		payment.setId(generateId());
		payment.setAmount(new BigDecimal(paymentRequestResource.getAmount()));
		payment.setOrderRefCode(paymentRequestResource.getOrderRefCode());
		payment.setPaymentRefCode(payRefCode);
		payment.setStatus(PaymentStatus.APPROVED.toString());
		payment.setCreatedDate(formatDate(new Date()));
		payment.setCreatedUser(authTokenFilter.getUsername());
		
		paymentRepository.save(payment);
		
	}
	
	private String externalPaymentServiceCall(String cardNumber, Integer cvv, Integer exMonth, Integer exYear, BigDecimal amount) {
		Random rnd = new Random();
	    int number = rnd.nextInt(99999999);
	    return "REF"+String.valueOf(number);
	}
	
	private int generateId() {
		List<Payment> paymentList = paymentRepository.findAll();
		List<Integer> paymentsList = new ArrayList<>();
		
		for(Payment payObject : paymentList) {
			paymentsList.add(payObject.getId());
		}
		return IdGenerator.generateIDs(paymentsList);	
	}
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}

	@Override
	public List<Payment> findAll() {
		return paymentRepository.findAll();
	}

}
