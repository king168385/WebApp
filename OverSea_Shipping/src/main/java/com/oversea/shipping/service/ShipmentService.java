package com.oversea.shipping.service;

import java.util.List;

import com.oversea.shipping.model.Shipment;

public interface ShipmentService {

	public List<Shipment> findAll();
	
	public Shipment findById(int theId);
	
	public void save(Shipment theShipment);
	
	public void deleteById(int theId);
	
}
