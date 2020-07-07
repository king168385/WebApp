package com.oversea.shipping.admin.controller;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oversea.shipping.model.PackageStatus;
import com.oversea.shipping.model.ShipDate;
import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.service.ShipDateService;
import com.oversea.shipping.service.ShipmentService;

@RestController
@RequestMapping("/admin/rest")
public class AjaxAdminRestController {
	@Autowired
	private ShipmentService shipmentService;

	@Autowired
	private ShipDateService shipDateService;

	@GetMapping(value = "/download/shipment")
	public ResponseEntity<InputStreamResource> excelShipment(@RequestParam(required = false) Integer shipDate_Id,
			@RequestParam(required = false) PackageStatus packageStatus) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  

		List<Shipment> theshipments = null;

		if (shipDate_Id == null) {
			if (packageStatus != null) {
				theshipments = shipmentService.findByStatus(packageStatus);
			}else {
				theshipments = shipmentService.findAll();
			}
		} else {
			ShipDate shipDate = shipDateService.findById(shipDate_Id);
			if (packageStatus != null) {
				theshipments = shipmentService.findByShipDateAndStatus(shipDate, packageStatus);
			} else {
				theshipments = shipmentService.findByShipDate(shipDate);
			}
		}

		ByteArrayInputStream in = shipmentService.exportToExcel(theshipments);
		// return IOUtils.toByteArray(in);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=shipments_"+dateFormat.format(new Date())+".xlsx");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@PostMapping("/downloadShipment")
	public ResponseEntity<Shipment> updatePackageStatus(@RequestBody String trackNumber) {
		Shipment theshipment = shipmentService.findByTrackingNumber(trackNumber);

		if (theshipment != null) {
			// save the shipment
			shipmentService.updatePackageStatus(theshipment);
		}

		return ResponseEntity.ok(theshipment);
	}
}
