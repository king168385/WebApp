package com.oversea.shipping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.model.ShipmentPackageStatus;
import com.oversea.shipping.service.ShipmentService;

@Controller
@RequestMapping("/rest")
public class RestController {
    
    @Autowired
    private ShipmentService shipmentService;

    @RequestMapping(value = "/shippingStatus", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody List<ShipmentPackageStatus> registration(HttpServletRequest request, HttpServletResponse response) {
        
        String trackingNumber = request.getParameter("trackingNumber");
        Shipment shipment = shipmentService.findByTrackingNumber(trackingNumber);
        List<ShipmentPackageStatus> packageStatusList= shipment.getPackageStatusList();
        
        return packageStatusList;
    }
}
