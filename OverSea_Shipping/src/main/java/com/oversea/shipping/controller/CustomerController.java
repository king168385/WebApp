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

import com.oversea.shipping.model.Customer;
import com.oversea.shipping.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

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

	@GetMapping("/update")
	public String showFormForUpdate(@RequestParam("customerId") int theId,
									Model theModel) {
		
		// get the customer from the service
		Customer thecustomer = customerService.findById(theId);
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", thecustomer);
		
		// send over to our form
		return "dashboard/customers/customer-form";			
	}
	
	
	@PostMapping("/save")
	public String savecustomer(@ModelAttribute("customer") Customer thecustomer) {
		
		// save the customer
		customerService.save(thecustomer);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/customers/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("customerId") int theId) {
		
		// delete the customer
		customerService.deleteById(theId);
		
		// redirect to /customers/list
		return "redirect:/customers/list";
		
	}
}


















