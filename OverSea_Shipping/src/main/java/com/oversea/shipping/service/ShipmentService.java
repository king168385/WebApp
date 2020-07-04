package com.oversea.shipping.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.oversea.shipping.model.Customer;
import com.oversea.shipping.model.Shipment;

public interface ShipmentService {

	public List<Shipment> findAll();
	
	public Shipment findByTrackingNumber(String trackingNumber);
	
	public void save(Shipment theShipment);
	
	@Transactional
	public void deleteByTrackingNumber(String trackingNumber);

	public List<Shipment> findByCustomer(Customer customer);

	public void updatePackageStatus(Shipment theshipment);
	
}
