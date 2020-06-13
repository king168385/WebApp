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
import com.oversea.shipping.model.ShipDate;
import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.service.CustomerService;
import com.oversea.shipping.service.ShipDateService;
import com.oversea.shipping.service.ShipmentService;

@Controller
@RequestMapping("/shipments")
public class ShipmentController {

	@Autowired
	private ShipDateService shipDateService;
	
	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private CustomerService customerService;
	
	// add mapping for "/list"

	@GetMapping("/list")
	public String listshipments(Model theModel) {
		
		// get shipments from db
		List<Shipment> theshipments = shipmentService.findAll();
		
		// add to the spring model
		theModel.addAttribute("shipments", theshipments);
		
		return "dashboard/shipments/list-shipments";
	}


	@GetMapping("/add")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Shipment theshipment = new Shipment();
				
		// get shipdate from db
		List<ShipDate> shipDateList = shipDateService.findAllActive();
				
		theModel.addAttribute("shipment", theshipment);
		theModel.addAttribute("shipDateList", shipDateList);
		
		return "dashboard/shipments/shipment-form";
	}
	
	@GetMapping("/addWithDate")
	public String showFormForAdd(@RequestParam("shipDate_Id") Integer shipDate_Id, Model theModel) {
		
		// create model attribute to bind form data
		Shipment theshipment = new Shipment();
				
		if(shipDate_Id != null) {
			ShipDate shipDate = shipDateService.findById(shipDate_Id);
			theshipment.setShipDate(shipDate);
		}

		// get shipdate from db
		List<ShipDate> shipDateList = shipDateService.findAllActive();
				
		theModel.addAttribute("shipment", theshipment);
		theModel.addAttribute("shipDateList", shipDateList);
		
		return "dashboard/shipments/shipment-form";
	}

	@GetMapping("/update")
	public String showFormForUpdate(@RequestParam("trackingNumber") String trackingNumber,
									Model theModel) {
		
		// get the shipment from the service
		Shipment theshipment = shipmentService.findByTrackingNumber(trackingNumber);
		
		List<ShipDate> shipDateList = shipDateService.findAllActive();
		
		// set shipment as a model attribute to pre-populate the form
		theModel.addAttribute("shipment", theshipment);
		theModel.addAttribute("shipDateList", shipDateList);
		
		// send over to our form
		return "dashboard/shipments/shipment-form";			
	}
	
	
	@PostMapping("/save")
	public String saveshipment(@ModelAttribute("shipment") Shipment theshipment) {
		
		if(theshipment.getCustomer().getId() == 0) {
			Customer customer = customerService.findByEmail(theshipment.getCustomer().getEmail());
			
			if(customer == null) {
				customerService.save(theshipment.getCustomer());
			}else {
				theshipment.setCustomer(customer);
			}
		}
		
		ShipDate shipDate = shipDateService.findById(theshipment.getShipDate().getId()); 
		theshipment.setUnit_price(shipDate.getUnitPrice());

		// save the shipment
		shipmentService.save(theshipment);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/shipments/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("trackingNumber") String trackingNumber) {
		
		// delete the shipment
		shipmentService.deleteByTrackingNumber(trackingNumber);
		
		// redirect to /shipments/list
		return "redirect:/shipments/list";
		
	}
}


















