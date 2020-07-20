package com.oversea.shipping.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oversea.shipping.auth.service.UserService;
import com.oversea.shipping.dao.RoleRepository;
import com.oversea.shipping.dao.ShipmentRepository;
import com.oversea.shipping.model.Customer;
import com.oversea.shipping.model.Role;
import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.model.ShipmentPackageStatus;
import com.oversea.shipping.model.User;
import com.oversea.shipping.service.CustomerService;
import com.oversea.shipping.service.ShipmentService;

@Controller
@RequestMapping("/rest")
public class RestController {
    
    @Autowired
    private ShipmentService shipmentService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ShipmentRepository shipmentRepository;
    
    @Autowired
    private CustomerService customerService;

    //@PostMapping(value = "/shippingstatus")
    @RequestMapping(value = "/shippingstatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ShipmentPackageStatus> getShippingStatusList(@RequestParam("trackingnumber") String trackingNumber) {
        
        User user = userService.getCurrentUser();

        Collection<Role> roles = user.getRoles();
        boolean isAdmin = roles.contains(roleRepository.findByName(Role.ADMIN));

        Shipment shipment = null;
        if (isAdmin) {
            shipment = shipmentService.findByTrackingNumber(trackingNumber);
        } else {
            Customer customer = user.getCustomer();
            shipment = shipmentRepository.findByCustomerAndTrackingNumber(customer, trackingNumber);
        }

        List<ShipmentPackageStatus> packageStatusList = null;
        if (shipment != null) {
            // TODO
            // login with: member@test.com
            // search tracking number: 773044700377098
            // only return 1 records, it's expecting 2 records (2 records in hipment_package_status table)
            packageStatusList = shipment.getPackageStatusList();
        }
        
        return packageStatusList;
    }
    
    //@GetMapping("/shipping/{trackingnumber}")
    @RequestMapping(value = "/shipping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ShipmentPackageStatus> getShippingList(@RequestParam("trackingnumber") String trackingNumber) {
        
        //Shipment shipment = shipmentService.findByTrackingNumber(trackingNumber);
        //List<ShipmentPackageStatus> packageStatusList= shipment.getPackageStatusList();
        
        User user = userService.getCurrentUser();

        Collection<Role> roles = user.getRoles();
        boolean isAdmin = roles.contains(roleRepository.findByName(Role.ADMIN));

        Shipment shipment = null;
        if (isAdmin) {
            shipment = shipmentService.findByTrackingNumber(trackingNumber);
        } else {
            Customer customer = user.getCustomer();
            shipment = shipmentRepository.findByCustomerAndTrackingNumber(customer, trackingNumber);
        }

        List<ShipmentPackageStatus> packageStatusList = null;
        if (shipment != null) {
            // TODO
            // login with: member@test.com
            // search tracking number: 773044700377098
            // only return 1 records, it's expecting 2 records (2 records in hipment_package_status table)
            packageStatusList = shipment.getPackageStatusList();
        }
        
        return packageStatusList;
    }
    
    //@GetMapping("/shipping/{trackingnumber}")
    @RequestMapping(value = "/shipping/{trackingnumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ShipmentPackageStatus> getShipping(@PathVariable(value = "trackingnumber") String trackingNumber) {
        
        //Shipment shipment = shipmentService.findByTrackingNumber(trackingNumber);
        //List<ShipmentPackageStatus> packageStatusList= shipment.getPackageStatusList();
        
        User user = userService.getCurrentUser();

        Collection<Role> roles = user.getRoles();
        boolean isAdmin = roles.contains(roleRepository.findByName(Role.ADMIN));

        Shipment shipment = null;
        if (isAdmin) {
            shipment = shipmentService.findByTrackingNumber(trackingNumber);
        } else {
            Customer customer = user.getCustomer();
            shipment = shipmentRepository.findByCustomerAndTrackingNumber(customer, trackingNumber);
        }

        List<ShipmentPackageStatus> packageStatusList = null;
        if (shipment != null) {
            // TODO
            // login with: member@test.com
            // search tracking number: 773044700377098
            // only return 1 records, it's expecting 2 records (2 records in hipment_package_status table)
            packageStatusList = shipment.getPackageStatusList();
        }
        
        return packageStatusList;
    }
}
