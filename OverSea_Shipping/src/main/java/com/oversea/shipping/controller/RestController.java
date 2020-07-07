package com.oversea.shipping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.model.ShipmentPackageStatus;
import com.oversea.shipping.service.ShipmentService;

@Controller
@RequestMapping("/admin")
public class RestController {
    
    @Autowired
    private ShipmentService shipmentService;

//    @RequestMapping(value = "/shippingStatus", params = {"trackingNumber"}, method = RequestMethod.POST, produces = "application/json")
    @PostMapping(value = "/shippingstatus/{trackingnumber}", produces = "application/json")
    @ResponseBody
    public List<ShipmentPackageStatus> getShippingStatusList(@PathVariable(value = "trackingnumber") String trackingNumber) {
        
        Shipment shipment = shipmentService.findByTrackingNumber(trackingNumber);
        List<ShipmentPackageStatus> packageStatusList= shipment.getPackageStatusList();
        
        return packageStatusList;
    }
}
