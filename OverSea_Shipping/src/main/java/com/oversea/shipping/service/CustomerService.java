package com.oversea.shipping.service;

import java.util.List;

import com.oversea.shipping.model.Customer;

public interface CustomerService {

	public List<Customer> findAll();
	
	public Customer findById(int theId);
	
	public Customer findByEmail(String email);
	
	public Customer findByWeChatId(String wechatId);
	
	public void save(Customer thecustomer);
	
	public void deleteById(int theId);
	
}
