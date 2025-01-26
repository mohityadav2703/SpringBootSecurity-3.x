package com.app.security.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.entity.Customer;
import com.app.security.service.CustomerService;

@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerService custService;

	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer c) {

		boolean status = custService.saveCustomer(c);
		if (status) {
			return new ResponseEntity<>("Success", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Customer c) {

		// pass credentials as a token
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(c.getEmail(), c.getPwd());

		Authentication authenticate = authManager.authenticate(token);
		boolean status = authenticate.isAuthenticated();
		if (status) {
			return new ResponseEntity<>("Login Successs", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Login Failed....", HttpStatus.BAD_REQUEST);
		}

	}
}
