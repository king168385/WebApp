package com.oversea.shipping.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oversea.shipping.model.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
	// add a method to sort by last name
	public List<Shipment> findAllByOrderByTrackingNumberAsc();
	
}
