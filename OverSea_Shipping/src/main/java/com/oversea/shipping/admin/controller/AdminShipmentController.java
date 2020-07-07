 package com.oversea.shipping.admin.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.oversea.shipping.model.PackageStatus;
import com.oversea.shipping.model.ShipDate;
import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.model.UpdatePackageStatus;
import com.oversea.shipping.service.ShipDateService;
import com.oversea.shipping.service.ShipmentService;
import com.oversea.shipping.service.ShipmentServiceImpl;

@Controller
@RequestMapping("/admin/shipments")
public class AdminShipmentController {

	@Autowired
	private ShipDateService shipDateService;
	
	@Autowired
	private ShipmentService shipmentService;
	
	Logger logger = LoggerFactory.getLogger(AdminShipmentController.class);
		
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
	
	@PostMapping("/uploadExcelFile")
	public String uploadFile(Model theModel, MultipartFile file) throws Exception {
	    InputStream input = file.getInputStream();
	    try {
	    	shipmentService.uploadShipmentFromExcel(input);
	    }catch(Exception e) {
	    	logger.error(e.getMessage(), e);
	    	theModel.addAttribute("alertMessage", e.getMessage());
	    	theModel.addAttribute("alertType", "danger");
	    }finally{
	    	input.close();
	    }
	    
	    UpdatePackageStatus updatePackageStatus = new UpdatePackageStatus();
		theModel.addAttribute("updatePackageStatus", updatePackageStatus);
	    
		List<ShipDate> shipDateList = shipDateService.findAllActive();
		theModel.addAttribute("shipDateList", shipDateList);
		
		List<Shipment> theshipments = new ArrayList<Shipment>();
		theModel.addAttribute("shipments", theshipments);	

		return "dashboard/shipments/list-shipments";
	}
}


















