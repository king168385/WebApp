package com.oversea.shipping.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.service.ShipmentService;

@Controller
@RequestMapping("/shipments")
public class ShipmentController {

	private ShipmentService shipmentService;
	
	public ShipmentController(ShipmentService theshipmentservice) {
		shipmentService = theshipmentservice;
	}
	
	// add mapping for "/list"

	@GetMapping("/list")
	public String listshipments(Model theModel) {
		
		// get shipments from db
		List<Shipment> theshipments = shipmentService.findAll();
		
		// add to the spring model
		theModel.addAttribute("shipments", theshipments);
		
		return "shipments/list-shipments";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Shipment theshipment = new Shipment();
		
		theModel.addAttribute("shipment", theshipment);
		
		return "shipments/shipment-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("shipmentId") int theId,
									Model theModel) {
		
		// get the shipment from the service
		Shipment theshipment = shipmentService.findById(theId);
		
		// set shipment as a model attribute to pre-populate the form
		theModel.addAttribute("shipment", theshipment);
		
		// send over to our form
		return "shipments/shipment-form";			
	}
	
	
	@PostMapping("/save")
	public String saveshipment(@ModelAttribute("shipment") Shipment theshipment) {
		
		// save the shipment
		shipmentService.save(theshipment);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/shipments/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("shipmentId") int theId) {
		
		// delete the shipment
		shipmentService.deleteById(theId);
		
		// redirect to /shipments/list
		return "redirect:/shipments/list";
		
	}
}


















