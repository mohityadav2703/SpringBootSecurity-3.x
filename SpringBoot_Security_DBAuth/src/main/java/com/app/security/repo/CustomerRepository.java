package com.app.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.security.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	public Customer findByEmail(String email);

}
