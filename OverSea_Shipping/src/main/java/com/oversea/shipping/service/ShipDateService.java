package com.oversea.shipping.service;

import java.util.List;

import com.oversea.shipping.model.ShipDate;

public interface ShipDateService {

	public List<ShipDate> findAll();
	
	public ShipDate findById(int theId);
	
	public void save(ShipDate theShipDate);
	
	public void deleteById(int theId);
	
}
