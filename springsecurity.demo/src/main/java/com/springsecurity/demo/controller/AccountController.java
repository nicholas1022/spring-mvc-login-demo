package com.springsecurity.demo.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springsecurity.demo.entity.Account;
import com.springsecurity.demo.entity.Customer;
import com.springsecurity.demo.repository.AccountsRepository;
import com.springsecurity.demo.repository.CustomersRepository;

@Controller
public class AccountController {
	
	//inject customers repository
	@Autowired
	private CustomersRepository customersRepository;
	
	//inject accounts repository
	@Autowired
	private AccountsRepository accountsRepository;
	

	
	@GetMapping("/accounts")
	public String showAccounts(Authentication authentication, @RequestParam("customerCode") long customerCode, Model theModel) {
		
		String role = null;
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			role = authentication.getAuthorities().toString();

		}
		
		Customer theCustomer = customersRepository.findByCustomerCode(BigInteger.valueOf(customerCode));
		
		int customerTier = theCustomer.getTier();
		
		switch(role) {
		case "[ROLE_manager]": {
			
			// get all accounts of a customer
			List<Account> allAccounts = accountsRepository.findByCustomer(theCustomer);
			
			if (allAccounts.isEmpty()) {

				theModel.addAttribute("noAccount", "This customer does not has any account.");
			}
			//add customer code to model
			theModel.addAttribute("customerCode", customerCode);
			
			// add accounts to the model
			theModel.addAttribute("allAccounts", allAccounts);
			
			return "accounts";

		}
		
		case "[ROLE_supervisor]": {
			
			if(customerTier == 2 || customerTier == 3) {
				// get all accounts of a customer
				List<Account> allAccounts = accountsRepository.findByCustomer(theCustomer);
				
				if (allAccounts.isEmpty()) {
	
					theModel.addAttribute("noAccount", "This customer does not has any account.");
				}
				//add customer code to model
				theModel.addAttribute("customerCode", customerCode);
				
				// add accounts to the model
				theModel.addAttribute("allAccounts", allAccounts);
				
				return "accounts";
			}	
		}
		
		case "[ROLE_employee]": {
			
			if(customerTier == 3) {
				// get all accounts of a customer
				List<Account> allAccounts = accountsRepository.findByCustomer(theCustomer);
				
				if (allAccounts.isEmpty()) {
	
					theModel.addAttribute("noAccount", "This customer does not has any account.");
				}
				//add customer code to model
				theModel.addAttribute("customerCode", customerCode);
				
				// add accounts to the model
				theModel.addAttribute("allAccounts", allAccounts);
				
				return "accounts";
			}
		}
	
		default:
			return "access-denied";
	}

}
	
	//add Account form
	@GetMapping("/addAccountForm")
	public String addAccountForm(@RequestParam("customerCode") long theCustomerCode, Model theModel) {
		
		// create model attribute to bind form data
		Account theAccount= new Account();
		
		theAccount.setCustomerId(BigInteger.valueOf(theCustomerCode));
		
		theModel.addAttribute("account", theAccount);
		
		return "account-form";
	}
	
	@GetMapping("/updateAccountForm")
	public String showFormForUpdate(Authentication authentication, @RequestParam("accountNumber") long theAccountNumber, Model theModel) {
		
		String role = null;
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			role = authentication.getAuthorities().toString();

		}
		
		Account theAccount = accountsRepository.findById(BigInteger.valueOf(theAccountNumber));
		
		int customerTier = theAccount.getCustomer().getTier();
		
		switch(role) {
			case "[ROLE_manager]": {
				// set customer as a model attribute to pre-populate the form
				theModel.addAttribute("account", theAccount);
				return "account-form";
			}
			
			case "[ROLE_supervisor]": {
				if(customerTier == 2 || customerTier == 3) {
					theModel.addAttribute("account", theAccount);
					return "account-form";
				}
			};
			break;
			
			case "[ROLE_employee]": {
				if(customerTier == 3) {
					theModel.addAttribute("account", theAccount);
					return "account-form";
				}
			};
			break;
			default:
			return "redirect:/accessDenied";
			
		}
		return "redirect:/accessDenied";

	}
	
	@PostMapping("/saveAccount")
	public String saveAccount(Authentication authentication, @ModelAttribute("account") Account theAccount) {
		
		String role = null;
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			role = authentication.getAuthorities().toString();
		}
		
		BigInteger accountId = theAccount.getId();
		
		BigInteger customerId = theAccount.getCustomerId();
		
		Account tempAccount ;
		
		int customerTier;
		
		if(accountId == null) {
			
			tempAccount = theAccount;
			
			Customer theCustomer = customersRepository.findByCustomerCode(customerId);
			
			tempAccount.setCustomer(theCustomer);
			
			customerTier = theCustomer.getTier();
			
		} else {
			
			tempAccount = accountsRepository.findById(accountId);
			
			customerTier = tempAccount.getCustomer().getTier();
		
		}
		
		switch(role) {
			case "[ROLE_manager]": {
				tempAccount.setBalance(theAccount.getBalance());
				// save the customer using our repository
				accountsRepository.save(tempAccount);
				customerId = tempAccount.getCustomer().getCustomerCode();
				return "redirect:/accounts?customerCode=" + customerId;
			}
			
			case "[ROLE_supervisor]": {
				if(customerTier == 2 || customerTier == 3) {
					tempAccount.setBalance(theAccount.getBalance());
					// save the customer using our repository
					accountsRepository.save(tempAccount);
					customerId = tempAccount.getCustomer().getCustomerCode();
					return "redirect:/accounts?customerCode=" + customerId;
				}
			};
			break;
			
			case "[ROLE_employee]": {
				if(customerTier == 3) {
					tempAccount.setBalance(theAccount.getBalance());
					// save the customer using our repository
					accountsRepository.save(tempAccount);
					customerId = tempAccount.getCustomer().getCustomerCode();
					return "redirect:/accounts?customerCode=" + customerId;
				}
			};
			break;
			default:
			return "access-denied";
			
		}
		return "access-denied";

	}

	
	@GetMapping("/deleteAccount")
	public String deleteAccount(Authentication authentication, @RequestParam("accountNumber") long theAccountNumber) {
		
		Account theAccount = accountsRepository.findById(BigInteger.valueOf(theAccountNumber));
		
		BigInteger customerCode = theAccount.getCustomer().getCustomerCode();
		
		Customer theCustomer = customersRepository.findByCustomerCode(customerCode);
		
		int customerTier = theCustomer.getTier();
		
		String role = null;
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			role = authentication.getAuthorities().toString();

		}
		
		switch(role) {
			case "[ROLE_manager]": {
				accountsRepository.deleteAccountById(BigInteger.valueOf(theAccountNumber));
				return "redirect:/accounts?customerCode=" + customerCode.toString();
			}
			
			case "[ROLE_supervisor]": {
				if(customerTier == 2 || customerTier == 3) {
					accountsRepository.deleteAccountById(BigInteger.valueOf(theAccountNumber));
					return "redirect:/accounts?customerCode=" + customerCode.toString();
				}
			};
			break;
			
			case "[ROLE_employee]": {
				if(customerTier == 3) {
					accountsRepository.deleteAccountById(BigInteger.valueOf(theAccountNumber));
					return "redirect:/accounts?customerCode=" + customerCode.toString();
				}
			};
			break;
		}
		return "redirect:/deleteDenied";
	}
}
