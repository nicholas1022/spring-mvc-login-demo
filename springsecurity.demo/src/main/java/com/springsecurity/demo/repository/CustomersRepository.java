package com.springsecurity.demo.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springsecurity.demo.entity.Customer;

@Transactional
public interface CustomersRepository extends Repository<Customer, BigInteger>{
	
	List<Customer> findByTier(int tier);
	
	List<Customer> findByTierBetween(int lowTier, int highTier);
	
	List<Customer> findAll();
	
	Customer findByCustomerCode(BigInteger theCustomerCode);
	
	void save(Customer theCustomer);
	
	void deleteCustomerByCustomerCode(BigInteger theCustomerCode);

}
