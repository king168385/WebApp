package com.oversea.shipping.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oversea.shipping.dao.CustomerRepository;
import com.oversea.shipping.dao.RoleRepository;
import com.oversea.shipping.dao.UserRepository;
import com.oversea.shipping.model.Customer;
import com.oversea.shipping.model.Role;
import com.oversea.shipping.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		Customer thecustomer = customerRepository.findByEmail(user.getUsername());
		
		if(thecustomer != null) user.setCustomer(thecustomer);
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(roleRepository.findByName(Role.MEMBER)));
		user.setActive(true);
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsernameAndActiveTrue(username);
	}

	@Override
	public User getCurrentUser() {
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		return findByUsername(currentUserName);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getCurrentRole(){
		List<String> roles = new ArrayList<String>();
		Iterator<SimpleGrantedAuthority> iterator = (Iterator<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator();
		while(iterator.hasNext()) {
			SimpleGrantedAuthority authority = iterator.next();
			String role = authority.getAuthority();
			roles.add(role);
		}
		return roles;
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
	}
}