package com.oversea.shipping.service;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oversea.shipping.dao.ShipmentRepository;
import com.oversea.shipping.model.Customer;
import com.oversea.shipping.model.PackageStatus;
import com.oversea.shipping.model.Shipment;
import com.oversea.shipping.model.ShipmentPackageStatus;

@Service
public class ShipmentServiceImpl implements ShipmentService {

	@Autowired
	private ShipmentRepository ShipmentRepository;

	public List<Shipment> findAll() {
		return ShipmentRepository.findAllByOrderByCreateDateDesc();
	}
	
	@Override
	public List<Shipment> findByCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return ShipmentRepository.findByCustomer(customer);
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
		
		if(unit < theShipment.getWeight()) {
			unit = theShipment.getWeight();
		}
		
		DecimalFormat format = new DecimalFormat("###.##");
		unit = Double.valueOf(format.format(unit));
		
		theShipment.setUnit(unit);
		theShipment.setShipping_price(unit * theShipment.getUnit_price());
		
		if(theShipment.getPackageStatusList().isEmpty()) {
			ShipmentPackageStatus status = new ShipmentPackageStatus();
			status.setPackageStatus(PackageStatus.NEW);
			theShipment.getPackageStatusList().add(status);
		}
		
		if(unit > 0 && !theShipment.hasPackageStatus(PackageStatus.RECEIVED)) {
			ShipmentPackageStatus status = new ShipmentPackageStatus();
			status.setPackageStatus(PackageStatus.RECEIVED);
			theShipment.getPackageStatusList().add(status);
		}
		
		ShipmentRepository.save(theShipment);
	}

	@Transactional
	public void deleteByTrackingNumber(String trackingNumber) {
		ShipmentRepository.deleteByTrackingNumber(trackingNumber);
	}

	

}






