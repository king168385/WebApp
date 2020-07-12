package com.oversea.shipping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oversea.shipping.auth.service.UserService;
import com.oversea.shipping.model.Customer;
import com.oversea.shipping.model.Role;
import com.oversea.shipping.model.User;
import com.oversea.shipping.service.CustomerService;

@Controller
@RequestMapping("/dashboard/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;

	// add mapping for "/list"

	@GetMapping("/update")
	public String showFormForUpdate(@RequestParam(required = false) Integer customerId,
									Model theModel) {
		Customer thecustomer = null;
		
		if(customerId == null) {
			User user = userService.getCurrentUser();
			thecustomer = user.getCustomer();
		}else {
			thecustomer = customerService.findById(customerId);
		}
		
		if(thecustomer == null) {
			thecustomer = new Customer();
		}
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", thecustomer);
		
		// send over to our form
		return "dashboard/customers/customer-form";			
	}
	
	
	@PostMapping("/save")
	public String savecustomer(@ModelAttribute("customer") Customer thecustomer) {
		List<String> roles = userService.getCurrentRole();
		// save the customer
		customerService.save(thecustomer);
		
		// use a redirect to prevent duplicate submissions
		if(roles.contains(Role.ADMIN)) {
			return "redirect:/admin/customers/list";
		}else {
			return "redirect:/dashboard";
		}
		
	}
}


















