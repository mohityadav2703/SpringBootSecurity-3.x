package com.app.security.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.security.entity.Customer;
import com.app.security.repo.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Autowired
	private CustomerRepository custRepo;
	
	
	//Register user
	public boolean saveCustomer(Customer c) {
		String encodedPwd= pwdEncoder.encode(c.getPwd());
		c.setPwd(encodedPwd);
		
		Customer cust=custRepo.save(c);
		return cust.getId()!=null;
	}

	/**
	 * it is a default method given by UserDetailsService to load the User from database
	 */
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer cust=custRepo.findByEmail(email);
						//email			password			Roles
		return new User(cust.getEmail(), cust.getPwd(), Collections.emptyList());
	}	

}
