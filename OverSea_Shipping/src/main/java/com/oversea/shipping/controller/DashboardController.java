package com.oversea.shipping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oversea.shipping.auth.service.UserService;
import com.oversea.shipping.model.Role;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping()
	public String loadupDashboard(Model theModel) {
//		List<String> roles = userService.getCurrentRole();
//
//		if(roles.contains(Role.ADMIN)) {
//			return "dashboard/dashboard";
//		}else {
//			return "dashboard/dashboard-welcome";
//		}
	    return "dashboard/dashboard";
	}
	
	@PostMapping()
	public String login(Model theModel) {
//		List<String> roles = userService.getCurrentRole();
//
//		if(roles.contains(Role.ADMIN)) {
//			return "dashboard/dashboard";
//		}else {
//			return "dashboard/dashboard-welcome";
//		}
	    return "dashboard/dashboard";
	}
}


















