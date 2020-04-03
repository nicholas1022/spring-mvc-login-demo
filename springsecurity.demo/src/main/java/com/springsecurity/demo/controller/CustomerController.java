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

import com.springsecurity.demo.entity.Customer;
import com.springsecurity.demo.repository.CustomersRepository;

@Controller
public class CustomerController {
	
	//inject customers repository
	@Autowired
	private CustomersRepository customersRepository;
	
	
	
	@GetMapping("/")
    public String home(Model theModel) {
		
		// get all customers
		List<Customer> allCustomers = customersRepository.findAll();
						
		// add customers to the model
		theModel.addAttribute("allCustomers", allCustomers);
		
		
		//get tier 2 & 3 customer
		List<Customer> tier2And3Customers = customersRepository.findByTierBetween(2, 3);
		
		// add customers to the model
		theModel.addAttribute("tier2And3Customers", tier2And3Customers);
        
		//get tier 2 & 3 customer
		List<Customer> tier3Customers = customersRepository.findByTier(3);
		
		// add customers to the model
		theModel.addAttribute("tier3Customers", tier3Customers);

        return "home";
    }
	
	//add customer form
	@GetMapping("/addCustomerForm")
	public String addCustomerForm(Model theModel) {
		
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(Authentication authentication, @ModelAttribute("customer") Customer theCustomer) {
		
		String role = null;
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			role = authentication.getAuthorities().toString();

		}
		
		int customerTier = theCustomer.getTier();
		
		switch(role) {
			case "[ROLE_manager]": {
				// save the customer using our repository
				customersRepository.save(theCustomer);
				return "redirect:/";
			}
			
			case "[ROLE_supervisor]": {
				if(customerTier == 2 || customerTier == 3) {
					// save the customer using our repository
					customersRepository.save(theCustomer);
					return "redirect:/";
				}
			};
			break;
			
			case "[ROLE_employee]": {
				if(customerTier == 3) {
					// save the customer using our repository
					customersRepository.save(theCustomer);
					return "redirect:/";
				}
			};
			break;
			default:
			return "access-denied";
			
		}
		return "access-denied";

	}
	
	@GetMapping("/updateCustomerForm")
	public String showFormForUpdate(Authentication authentication, @RequestParam("customerCode") int theCustomerCode, Model theModel) {
		
		String role = null;
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			role = authentication.getAuthorities().toString();

		}
		
		// get the customer from our service
		Customer theCustomer = customersRepository.findByCustomerCode(BigInteger.valueOf(theCustomerCode));
		
		int customerTier = theCustomer.getTier();
		
		switch(role) {
			case "[ROLE_manager]": {
				// set customer as a model attribute to pre-populate the form
				theModel.addAttribute("customer", theCustomer);
				return "customer-form";
			}
			
			case "[ROLE_supervisor]": {
				if(customerTier == 2 || customerTier == 3) {
					theModel.addAttribute("customer", theCustomer);
					return "customer-form";
				}
			};
			break;
			
			case "[ROLE_employee]": {
				if(customerTier == 3) {
					theModel.addAttribute("customer", theCustomer);
					return "customer-form";
				}
			};
			break;
			default:
			return "access-denied";
			
		}
		return "access-denied";

	}

	@GetMapping("/deleteCustomer")
	public String deleteCustomer(Authentication authentication, @RequestParam("customerCode") long theCustomerCode) {
		
		Customer theCustomer = customersRepository.findByCustomerCode(BigInteger.valueOf(theCustomerCode));

		int customerTier = theCustomer.getTier();
		
		String role = null;
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			role = authentication.getAuthorities().toString();

		}
		
		switch(role) {
			case "[ROLE_manager]": {
				customersRepository.deleteCustomerByCustomerCode(BigInteger.valueOf(theCustomerCode));
				return "redirect:/";
			}
			
			case "[ROLE_supervisor]": {
				if(customerTier == 2 || customerTier == 3) {
					customersRepository.deleteCustomerByCustomerCode(BigInteger.valueOf(theCustomerCode));
					return "redirect:/";
				}
			};
			break;
			
			case "[ROLE_employee]": {
				if(customerTier == 3) {
					customersRepository.deleteCustomerByCustomerCode(BigInteger.valueOf(theCustomerCode));
					return "redirect:/";
				}
			};
			break;
			default:
				return "redirect:/deleteDenied";
		}
		return "redirect:/deleteDenied";
	}
}
