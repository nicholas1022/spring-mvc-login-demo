package com.springsecurity.demo.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_code")
	private BigInteger customerCode;
	
	@Column(name="name")
	private String name;
	
	@Column(name="tier")
	private int tier;
	
	@OneToMany(mappedBy="customer", cascade= {CascadeType.ALL})
	private List<Account> accounts;
	
	public Customer() {

	}

	public Customer(BigInteger customerCode, String name, int tier) {
		this.customerCode = customerCode;
		this.name = name;
		this.tier = tier;
	}

	public BigInteger getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(BigInteger customerCode) {
		this.customerCode = customerCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	@Override
	public String toString() {
		return "Customer [customerCode=" + customerCode + ", name=" + name + ", tier=" + tier + "]";
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	// add convenience methods for bi-directional relationship
	
	public void add(Account tempAccount) {
			
		if (accounts == null) {
				accounts = new ArrayList<>();
			}
			
		accounts.add(tempAccount);
			
			tempAccount.setCustomer(this);
		}

}
