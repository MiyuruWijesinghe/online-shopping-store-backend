package com.spm.onlineshopping.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spm.onlineshopping.model.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, Integer> {

}
