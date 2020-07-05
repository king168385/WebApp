package com.oversea.shipping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oversea.shipping.auth.service.UserService;
import com.oversea.shipping.dao.CustomerRepository;
import com.oversea.shipping.model.Customer;
import com.oversea.shipping.model.Role;
import com.oversea.shipping.model.User;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserService userService;

	public List<Customer> findAll() {
		return customerRepository.findAllByOrderByLastNameAsc();
	}

	public Customer findById(int theId) {
		Optional<Customer> result = customerRepository.findById(theId);
		
		Customer thecustomer = null;
		
		if (result.isPresent()) {
			thecustomer = result.get();
		}
		else {
			// we didn't find the customer
			throw new RuntimeException("Did not find customer id - " + theId);
		}
		
		return thecustomer;
	}
	
	public Customer findByEmail(String email) {
		Customer thecustomer = customerRepository.findByEmail(email);
		return thecustomer;
	}

	public void save(Customer thecustomer) {
		customerRepository.save(thecustomer);
		
		User user = userService.getCurrentUser();

		if(user.hasRole(Role.MEMBER) && user.getCustomer() == null) {
			user.setCustomer(thecustomer);
			userService.update(user);
		}
	}

	public void deleteById(int theId) {
		customerRepository.deleteById(theId);
	}

	@Override
	public Customer findByWeChatId(String wechatId) {
		return customerRepository.findByWechatId(wechatId);
	}

}






