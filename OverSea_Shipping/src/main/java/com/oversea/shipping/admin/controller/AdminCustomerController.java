package com.oversea.shipping.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oversea.shipping.model.Customer;
import com.oversea.shipping.service.CustomerService;

@Controller
@RequestMapping("/admin/customers")
public class AdminCustomerController {

	@Autowired
	private CustomerService customerService;

	// add mapping for "/list"

	@GetMapping("/list")
	public String listcustomers(Model theModel) {
		
		// get customers from db
		List<Customer> thecustomers = customerService.findAll();
		
		// add to the spring model
		theModel.addAttribute("customers", thecustomers);
		
		return "dashboard/customers/list-customers";
	}
	
	@GetMapping("/add")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer thecustomer = new Customer();
		
		theModel.addAttribute("customer", thecustomer);
		
		return "dashboard/customers/customer-form";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("customerId") int theId) {
		
		// delete the customer
		customerService.deleteById(theId);
		
		// redirect to /customers/list
		return "redirect:/admin/customers/list";
		
	}
}


















