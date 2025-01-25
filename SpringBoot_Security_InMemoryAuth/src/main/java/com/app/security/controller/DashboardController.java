package com.app.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

	@GetMapping("/")
	public String login() {
		return "Welcome to Your Login Page";
	}

	@GetMapping("/welcome")
	public String getWelcome() {
		return "Welcome to Spring Security Application";
	}
	
	@GetMapping("/greet")
	public String getGreet() {
		return "Good Morning";
	}

}
