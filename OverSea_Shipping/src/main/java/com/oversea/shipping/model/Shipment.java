package com.oversea.shipping.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="shipment")
public class Shipment {

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@Id
	@Column(name="trackingNumber")
	private String trackingNumber;
	
	@Column(name="weight")
	private double weight;
	
	@Column(name="height")
	private double height;
	
	@Column(name="length")
	private double length;
	
	@Column(name="width")
	private double width;
	
	@Column(name="shipping_price")
	private double shipping_price;
	
	@Column(name="unit_price")
	private double unit_price;
	
	@Column(name="unit")
	private double unit;

	public Shipment(Customer customer, String trackingNumber, double weight, double height, double length, double width,
			double shipping_price, double unit_price, double unit) {
		super();
		this.customer = customer;
		this.trackingNumber = trackingNumber;
		this.weight = weight;
		this.height = height;
		this.length = length;
		this.width = width;
		this.shipping_price = shipping_price;
		this.unit_price = unit_price;
		this.unit = unit;
	}

	public Shipment() {
		// TODO Auto-generated constructor stub
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getShipping_price() {
		return shipping_price;
	}

	public void setShipping_price(double shipping_price) {
		this.shipping_price = shipping_price;
	}

	public double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	public double getUnit() {
		return unit;
	}

	public void setUnit(double unit) {
		this.unit = unit;
	}
	
	
	
}
