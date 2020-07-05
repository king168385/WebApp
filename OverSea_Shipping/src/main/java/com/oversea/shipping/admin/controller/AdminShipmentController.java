 package com.oversea.shipping.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oversea.shipping.model.PackageStatus;
import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.model.UpdatePackageStatus;
import com.oversea.shipping.service.ShipmentService;

@Controller
@RequestMapping("/admin/shipments")
public class AdminShipmentController {

	@Autowired
	private ShipmentService shipmentService;
		
	// add mapping for "/list"

	
	@PostMapping("/updatePackageStatus")
	public String updatePackageStatus(UpdatePackageStatus updatePackageStatus) {
		if(updatePackageStatus != null && updatePackageStatus.getTrackingNumbers() != null && updatePackageStatus.getPackageStatus() != null) {
			PackageStatus status = updatePackageStatus.getPackageStatus();
			for(String trackingNumber: updatePackageStatus.getTrackingNumbers()) {
				Shipment shipment = shipmentService.findByTrackingNumber(trackingNumber);
				if(shipment != null)
					shipmentService.updatePackageStatus(shipment, status);
			}
		}

		// use a redirect to prevent duplicate submissions
		return "redirect:/dashboard/shipments/list";
	}
}


















