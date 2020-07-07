package com.oversea.shipping.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oversea.shipping.model.Customer;
import com.oversea.shipping.model.PackageStatus;
import com.oversea.shipping.model.ShipDate;
import com.oversea.shipping.model.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
	// add a method to sort by last name
	public List<Shipment> findAllByOrderByCreateDateDesc();
	
	public Shipment findByTrackingNumber(String trackingNumber);
	
	public void deleteByTrackingNumber(String trackingNumber);

	public List<Shipment> findByCustomer(Customer customer);
	
	public Shipment findByCustomerAndTrackingNumber(Customer customer, String trackingNumber);
	
	public List<Shipment> findByShipDate(ShipDate shipDate);
	
	public List<Shipment> findByStatus(PackageStatus status);
	
	public List<Shipment> findByShipDateAndStatus(ShipDate shipDate, PackageStatus status);
	
}
