package com.springsecurity.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String showLoginPage() {
		
		return "login";
		
	}
	
	//add request mapping to /access-denied
	@GetMapping("/access-denied")
	public String showAccessDeniedPage() {
		
		return "access-denied";
		
	}
	
	@GetMapping("/deleteDenied")
	public String deleteAccessDenied(){
		
		return "delete-denied";
	}

}
