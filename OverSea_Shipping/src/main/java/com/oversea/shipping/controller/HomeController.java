package com.oversea.shipping.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oversea.shipping.model.ShipDate;
import com.oversea.shipping.service.CustomerService;
import com.oversea.shipping.service.ShipDateService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	private ShipDateService shipDateService;
	
	public HomeController(ShipDateService theshipdateService) {
		shipDateService = theshipdateService;
	}

	@GetMapping()
	public String home(Model theModel) {

		// get shipdate from db
		List<ShipDate> theshipdate = shipDateService.findAllActive();
				
		// add to the spring model
		theModel.addAttribute("shipDate", theshipdate);
				
		return "site/home";
	}
}


















