package com.oversea.shipping.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.service.ShipmentService;
	
@RestController
@RequestMapping("/admin/rest")
public class AjaxAdminRestController {
	@Autowired
	private ShipmentService shipmentService;
	
	@PostMapping("/updatePackageStatus")
	public ResponseEntity<Shipment> updatePackageStatus(@RequestBody String trackNumber) {
		Shipment theshipment = shipmentService.findByTrackingNumber(trackNumber);
		
		if(theshipment != null) {
			// save the shipment
			shipmentService.updatePackageStatus(theshipment);
		}
		
		return ResponseEntity.ok(theshipment);
	}
}
