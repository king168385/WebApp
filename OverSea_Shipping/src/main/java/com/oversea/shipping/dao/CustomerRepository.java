package com.oversea.shipping.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oversea.shipping.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	// add a method to sort by last name
	public List<Customer> findAllByOrderByLastNameAsc();
	
	public Customer findByEmail(String email);
	
}
