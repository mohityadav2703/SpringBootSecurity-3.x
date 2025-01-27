package com.app.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.security.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
	
	public CustomerEntity findByname(String name);

}
