package com.oversea.shipping.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name="shipment")
public class Shipment {
	@NotNull
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@NotNull
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "ship_date_id")
	private ShipDate shipDate;
	
	@Id
	@NotEmpty(message = "必须填写")
	@Column(name="trackingNumber", unique=true, nullable = false)
	private String trackingNumber;
	
	@Column(name="shipingCompany")
	private String shipingCompany;
	
	@Column(name="weight")
	private double weight;
	
	@Column(name="height")
	private double height;
	
	@Column(name="length")
	private double length;
	
	@Column(name="width")
	private double width;
	
	@Column(name="package_value")
	private double packageValue;
	
	@Column(name="shipping_price")
	private double shipping_price;
	
	@Column(name="unit_price")
	private double unit_price;
	
	@Column(name="unit")
	private double unit;
	
	@Column(name="description")
	private String description;
	
	@NotEmpty(message = "必须填写")
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

	@Column(name="note")
	private String note;
	
	@Column(name="status")
	private PackageStatus status;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "shipment_package_status_Id")
	private List<ShipmentPackageStatus> packageStatusList = new ArrayList<ShipmentPackageStatus>();
	
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

	public String getShipingCompany() {
		return shipingCompany;
	}

	public void setShipingCompany(String shipingCompany) {
		this.shipingCompany = shipingCompany;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(double packageValue) {
		this.packageValue = packageValue;
	}

	public List<ShipmentPackageStatus> getPackageStatusList() {
		return packageStatusList;
	}

	public void setPackageStatusList(List<ShipmentPackageStatus> packageStatusList) {
		this.packageStatusList = packageStatusList;
	}
	
	public PackageStatus getStatus() {
		return status;
	}

	public void setStatus(PackageStatus status) {
		this.status = status;
	}
	
	public boolean hasPackageStatus(PackageStatus packageStatus) {
		boolean result = false;
		for(ShipmentPackageStatus status: packageStatusList) {
			if(status.getPackageStatus().equals(packageStatus)) {
				result = true;
			}
		}
		return result;
	}
	
	public void addPackageStatus(PackageStatus packageStatus) {
		ShipmentPackageStatus result = null;
		for(ShipmentPackageStatus status: packageStatusList) {
			if(status.getPackageStatus().equals(packageStatus)) {
				result = status;
			}
		}
		
		if (result != null) {
			result.setCreateDate(new Date());
		} else {
			ShipmentPackageStatus newStatus = new ShipmentPackageStatus();
			newStatus.setPackageStatus(packageStatus);
			packageStatusList.add(newStatus);
		}
		status = packageStatus;
	}

	
	
	
}
