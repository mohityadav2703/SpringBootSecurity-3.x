package com.app.security.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.entity.CustomerEntity;
import com.app.security.repo.CustomerRepository;
import com.app.security.service.CustomerService;
import com.app.security.service.JwtService;

@RestController
public class CustomerController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private JwtService jwt;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Hello Welcome to Spring Security Application with JWT Token..";
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<String> saveCustomer(@RequestBody CustomerEntity cust){
		boolean status=custService.saveUser(cust);
		if(status) {
			return new ResponseEntity<>("Customer Details Saved...",HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("Customer Details Not Saved....",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody CustomerEntity cust){
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(cust.getName(), cust.getPwd());
		try {
			Authentication auth= authManager.authenticate(token);
			
			if(auth.isAuthenticated()) {
				String jwtToken=jwt.generateToken(cust.getName());
				return new ResponseEntity<>(jwtToken, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
		}
		return new ResponseEntity<>("Invalid Credentials", HttpStatus.BAD_REQUEST);
	}

}
