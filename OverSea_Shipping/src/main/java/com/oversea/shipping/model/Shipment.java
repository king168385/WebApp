package com.oversea.shipping.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name="shipment")
public class Shipment {

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "ship_date_id")
	private ShipDate shipDate;
	
	@Id
	@Column(name="trackingNumber", unique=true, nullable = false)
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
	
	@Column(name="description")
	private String description;
	
	@Column(name="deliveryMethod")
	private String deliveryMethod;
	
	@Column(name="deliveryAddress")
	private String deliveryAddress;
	
	@Column(name="deliveryCity")
	private String deliveryCity;
	
	@Column(name="deliveryProvince")
	private String deliveryProvince;
	
	@Column(name="deliveryPostCode")
	private String deliveryPostCode;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false)
	private Date createDate;
	
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "pickupLocation_Id")
	private PickUpLocation pickupLocation;

	
	
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

	public ShipDate getShipDate() {
		return shipDate;
	}

	public void setShipDate(ShipDate shipDate) {
		this.shipDate = shipDate;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}

	public String getDeliveryProvince() {
		return deliveryProvince;
	}

	public void setDeliveryProvince(String deliveryProvince) {
		this.deliveryProvince = deliveryProvince;
	}

	public String getDeliveryPostCode() {
		return deliveryPostCode;
	}

	public void setDeliveryPostCode(String deliveryPostCode) {
		this.deliveryPostCode = deliveryPostCode;
	}

	public PickUpLocation getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(PickUpLocation pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
