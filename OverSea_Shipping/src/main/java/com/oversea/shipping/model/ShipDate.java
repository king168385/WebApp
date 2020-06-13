package com.oversea.shipping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ship_date")
public class ShipDate {

	// define fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="shipping_date")
	private String shippingDate;
	
	@Column(name="cutoff_date")
	private String cutOffDate;
	
	@Column(name="destination")
	private String destination;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="unit_price")
	private double unitPrice;

	// define constructors
	
	public ShipDate() {
		
	}

	public ShipDate(int id, String shippingDate, String cutOffDate, String destination, Boolean active) {
		super();
		this.id = id;
		this.shippingDate = shippingDate;
		this.cutOffDate = cutOffDate;
		this.destination = destination;
		this.active = active;
	}

	public ShipDate(String shippingDate, String cutOffDate, String destination, Boolean active) {
		super();
		this.shippingDate = shippingDate;
		this.cutOffDate = cutOffDate;
		this.destination = destination;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getCutOffDate() {
		return cutOffDate;
	}

	public void setCutOffDate(String cutOffDate) {
		this.cutOffDate = cutOffDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}	
}











