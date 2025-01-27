package com.app.security.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.security.entity.CustomerEntity;
import com.app.security.repo.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder pEncoder;
	
	@Autowired
	private CustomerRepository custRepo;
	
	
	public boolean saveUser(CustomerEntity cust) {
		
		String encodedPwd=pEncoder.encode(cust.getPwd());
		cust.setPwd(encodedPwd);
		
		CustomerEntity custData= custRepo.save(cust);
		return custData.getId()!=null;
	}


	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		CustomerEntity cust=custRepo.findByUsername(name);
		return new User(cust.getName(), cust.getPwd(), Collections.emptyList());
	}

}
