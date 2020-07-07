package com.oversea.shipping.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oversea.shipping.model.ShipDate;

public interface ShipDateRepository extends JpaRepository<ShipDate, Integer> {
	// add a method to sort by last name
	public List<ShipDate> findAllByOrderByShippingDateAsc();
	
	public List<ShipDate> findByCutOffDateGreaterThanEqualAndActiveTrue(String date);
	
	public ShipDate findByShippingDateAndActiveTrue(String date);
	
}
