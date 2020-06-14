package com.oversea.shipping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	// add mapping for "/list"
	
	@GetMapping()
	public String loadupDashboard(Model theModel) {
		
		return "dashboard/dashboard";
	}
	
	@PostMapping()
	public String login(Model theModel) {
		
		return "dashboard/dashboard";
	}
}


















