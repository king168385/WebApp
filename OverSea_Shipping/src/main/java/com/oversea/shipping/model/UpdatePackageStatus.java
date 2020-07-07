package com.oversea.shipping.model;


public class UpdatePackageStatus {
	private String[] trackingNumbers;
	private PackageStatus packageStatus;
	

	public String[] getTrackingNumbers() {
		return trackingNumbers;
	}
	public void setTrackingNumbers(String[] trackingNumbers) {
		this.trackingNumbers = trackingNumbers;
	}
	public PackageStatus getPackageStatus() {
		return packageStatus;
	}
	public void setPackageStatus(PackageStatus packageStatus) {
		this.packageStatus = packageStatus;
	}
	
	
}
