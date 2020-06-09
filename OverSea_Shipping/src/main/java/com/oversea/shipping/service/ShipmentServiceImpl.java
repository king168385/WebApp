package com.oversea.shipping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public Shipment findByTrackingNumber(String trackingNumber) {
		Shipment theShipment = ShipmentRepository.findByTrackingNumber(trackingNumber);
	
		if (theShipment == null) {
			// we didn't find the Shipment
			throw new RuntimeException("Did not find tracking Number - " + trackingNumber);
		}
		
		return theShipment;
	}

	public void save(Shipment theShipment) {
		
		double unit = theShipment.getLength() * theShipment.getWidth() * theShipment.getHeight() / 6000;
		
		if(unit > theShipment.getWeight()) {
			theShipment.setUnit(unit);
		}else {
			theShipment.setUnit(theShipment.getWeight());
		}
		
		ShipmentRepository.save(theShipment);
	}

	@Transactional
	public void deleteByTrackingNumber(String trackingNumber) {
		ShipmentRepository.deleteByTrackingNumber(trackingNumber);
	}

}






