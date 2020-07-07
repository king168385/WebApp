package com.oversea.shipping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.model.ShipmentPackageStatus;
import com.oversea.shipping.service.ShipmentService;

@Controller
@RequestMapping("/rest")
public class RestController {
    
    @Autowired
    private ShipmentService shipmentService;

//    @RequestMapping(value = "/shippingStatus", params = {"trackingNumber"}, method = RequestMethod.POST, produces = "application/json")
    @PostMapping(value = "/shippingstatus")
    @ResponseBody
    public List<ShipmentPackageStatus> getShippingStatusList(@RequestParam("trackingnumber") String trackingnumber) {
        
        Shipment shipment = shipmentService.findByTrackingNumber(trackingnumber);
        List<ShipmentPackageStatus> packageStatusList= shipment.getPackageStatusList();
        
        return packageStatusList;
    }
    
    @GetMapping("/shipping/{trackingnumber}")
    @ResponseBody
    public List<ShipmentPackageStatus> getShippingList(@PathVariable(value = "trackingnumber") String trackingnumber) {
        Shipment shipment = shipmentService.findByTrackingNumber(trackingnumber);
        List<ShipmentPackageStatus> packageStatusList= shipment.getPackageStatusList();
        
        return packageStatusList;
    }
}
