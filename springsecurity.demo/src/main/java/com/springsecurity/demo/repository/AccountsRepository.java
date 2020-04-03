package com.springsecurity.demo.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springsecurity.demo.entity.Account;
import com.springsecurity.demo.entity.Customer;

@Transactional
public interface AccountsRepository extends Repository<Account, BigInteger>{
	
	List<Account> findByCustomer(Customer customer);
	
	Account findById(BigInteger id);
	
	void deleteAccountById(BigInteger id);
	
	void save(Account account);
	

}
