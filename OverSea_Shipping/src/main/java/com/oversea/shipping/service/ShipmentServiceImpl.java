package com.oversea.shipping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oversea.shipping.dao.ShipmentRepository;
import com.oversea.shipping.model.Shipment;

@Service
public class ShipmentServiceImpl implements ShipmentService {

	private ShipmentRepository ShipmentRepository;
	
	@Autowired
	public ShipmentServiceImpl(ShipmentRepository theShipmentRepository) {
		ShipmentRepository = theShipmentRepository;
	}
	
	public List<Shipment> findAll() {
		return ShipmentRepository.findAllByOrderByTrackingNumberAsc();
	}

	public Shipment findById(int theId) {
		Optional<Shipment> result = ShipmentRepository.findById(theId);
		
		Shipment theShipment = null;
		
		if (result.isPresent()) {
			theShipment = result.get();
		}
		else {
			// we didn't find the Shipment
			throw new RuntimeException("Did not find Shipment id - " + theId);
		}
		
		return theShipment;
	}

	public void save(Shipment theShipment) {
		ShipmentRepository.save(theShipment);
	}

	public void deleteById(int theId) {
		ShipmentRepository.deleteById(theId);
	}

}






