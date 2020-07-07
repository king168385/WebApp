 package com.oversea.shipping.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.oversea.shipping.auth.service.UserService;
import com.oversea.shipping.model.Customer;
import com.oversea.shipping.model.PackageStatus;
import com.oversea.shipping.model.Role;
import com.oversea.shipping.model.ShipDate;
import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.model.UpdatePackageStatus;
import com.oversea.shipping.model.User;
import com.oversea.shipping.service.CustomerService;
import com.oversea.shipping.service.ShipDateService;
import com.oversea.shipping.service.ShipmentService;

@Controller
@SessionAttributes("shipment")
@RequestMapping("/dashboard/shipments")
public class ShipmentController {

	@Autowired
	private ShipDateService shipDateService;
	
	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
	// add mapping for "/list"

	@GetMapping("/list")
	public String listshipments(@RequestParam(required = false) Integer shipDate_Id, @RequestParam(required = false) PackageStatus packageStatus, Model theModel) {
		User user = userService.getCurrentUser();
		
		List<Shipment> theshipments = null;
		
		if(user.hasRole(Role.EMPLOYEE) || user.hasRole(Role.ADMIN)) {
			if(shipDate_Id == null) {
				theshipments = new ArrayList<Shipment>();
			}else {
				ShipDate shipDate = shipDateService.findById(shipDate_Id);
				if(packageStatus != null) {
					theshipments = shipmentService.findByShipDateAndStatus(shipDate, packageStatus);
				}else {
					theshipments = shipmentService.findByShipDate(shipDate);
				}
			}
			
			UpdatePackageStatus updatePackageStatus = new UpdatePackageStatus();
			theModel.addAttribute("updatePackageStatus", updatePackageStatus);
			
			List<ShipDate> shipDateList = shipDateService.findAllActive();
			theModel.addAttribute("shipDateList", shipDateList);
			
			theModel.addAttribute("shippingDateSearch", shipDate_Id);
			theModel.addAttribute("packageStatusSearch", packageStatus);
		}else {
			theshipments = shipmentService.findByCustomer(user.getCustomer());
		}

		// add to the spring model
		theModel.addAttribute("shipments", theshipments);		
		
		return "dashboard/shipments/list-shipments";
	}


	@GetMapping("/add")
	public String showFormForAdd(Model theModel) {
		User user = userService.getCurrentUser();
		
		// create model attribute to bind form data
		Shipment theshipment = new Shipment();
		theshipment.setCustomer(user.getCustomer());
				
		// get shipdate from db
		List<ShipDate> shipDateList = shipDateService.findAllActive();
				
		theModel.addAttribute("shipment", theshipment);
		theModel.addAttribute("shipDateList", shipDateList);
		
		return "dashboard/shipments/shipment-form";
	}
	
	@GetMapping("/addWithDate")
	public String showFormForAdd(@RequestParam("shipDate_Id") Integer shipDate_Id, Model theModel) {
		User user = userService.getCurrentUser();
		
		// create model attribute to bind form data
		Shipment theshipment = new Shipment();
		theshipment.setCustomer(user.getCustomer());
				
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
	public String saveshipment( Model theModel, @Valid Shipment shipment, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			List<ShipDate> shipDateList = shipDateService.findAllActive();
			theModel.addAttribute("shipDateList", shipDateList);
            return "dashboard/shipments/shipment-form";
        }
		
		if(shipment.getCustomer().getId() == 0) {
			Customer customer = customerService.findByEmail(shipment.getCustomer().getEmail());
			
			if(customer == null) {
				customerService.save(shipment.getCustomer());
			}else {
				shipment.setCustomer(customer);
			}
		}

		// save the shipment
		shipmentService.save(shipment);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/dashboard/shipments/list";
	}
	

	@GetMapping("/delete")
	public String delete(@RequestParam("trackingNumber") String trackingNumber) {
		
		// delete the shipment
		shipmentService.deleteByTrackingNumber(trackingNumber);
		
		// redirect to /shipments/list
		return "redirect:/dashboard/shipments/list";
		
	}
}


















