package com.oversea.shipping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oversea.shipping.service.CustomerService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	public HomeController(CustomerService thecustomerService) {
	}

	@GetMapping()
	public String home(Model theModel) {

		return "home/home";
	}
}


















