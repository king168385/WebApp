package com.lean.Spring.hb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lean.Spring.hb.dao.CustomerRepository;
import com.lean.Spring.hb.entity.Customer;


@Service
public class CustomerServiceJpaImpl implements CustomerService {

	// need to inject customer dao
	@Autowired
	private CustomerRepository customerDAO;
	
	@Override
	@Transactional
	public List<Customer> getCustomers() {
		return customerDAO.findAll();
	}

	@Override
	@Transactional
	public void saveCustomer(Customer theCustomer) {

		customerDAO.save(theCustomer);
	}

	@Override
	@Transactional
	public Customer getCustomer(int theId) {
		Optional<Customer> result = customerDAO.findById(theId);
		
		Customer theCustomer = null;
		
		if(result.isPresent()) {
			theCustomer =  result.get();
		}
		
		return theCustomer;
	}

	@Override
	@Transactional
	public void deleteCustomer(int theId) {
		
		customerDAO.deleteById(theId);
	}
}





