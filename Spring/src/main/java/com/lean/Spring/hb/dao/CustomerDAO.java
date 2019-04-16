package com.lean.Spring.hb.dao;

import java.util.List;

import com.lean.Spring.hb.entity.Customer;



public interface CustomerDAO {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);

	public void deleteCustomer(int theId);
	
}
