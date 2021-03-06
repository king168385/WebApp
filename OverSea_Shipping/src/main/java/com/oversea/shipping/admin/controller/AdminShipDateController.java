 package com.oversea.shipping.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oversea.shipping.model.ShipDate;
import com.oversea.shipping.service.ShipDateService;

@Controller
@RequestMapping("/admin/shipdate")
public class AdminShipDateController {

	@Autowired
	private ShipDateService shipDateService;
		
	// add mapping for "/list"

	@GetMapping("/list")
	public String listshipdate(Model theModel) {
		
		// get shipdate from db
		List<ShipDate> theshipdate = shipDateService.findAll();
		
		// add to the spring model
		theModel.addAttribute("shipDate", theshipdate);
		
		return "dashboard/shipdate/list-shipDate";
	}
	
	@GetMapping("/add")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		ShipDate theshipDate = new ShipDate();
		
		theModel.addAttribute("shipDate", theshipDate);
		
		return "dashboard/shipdate/shipDate-form";
	}

	@GetMapping("/update")
	public String showFormForUpdate(@RequestParam("shipDateId") int theId,
									Model theModel) {
		
		// get the shipDate from the service
		ShipDate theshipDate = shipDateService.findById(theId);
		
		// set shipDate as a model attribute to pre-populate the form
		theModel.addAttribute("shipDate", theshipDate);
		
		// send over to our form
		return "dashboard/shipdate/shipDate-form";			
	}
	
	
	@PostMapping("/save")
	public String saveshipDate(@ModelAttribute("shipDate") ShipDate theshipDate) {
		
		// save the shipDate
		shipDateService.save(theshipDate);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/admin/shipdate/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("shipDateId") int theId) {
		
		// delete the shipDate
		shipDateService.deleteById(theId);
		
		// redirect to /shipdate/list
		return "redirect:/admin/shipdate/list";
		
	}
}


















