package com.lean.usaa.ods;

public class ODSUpdateCreditBureauRequest {
	private String accountNumber;
	private String creditBureauAccountStatus;
	
	public ODSUpdateCreditBureauRequest(){
		
	}

	
	public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public String getCreditBureauAccountStatus() {
		return creditBureauAccountStatus;
	}

	public void setCreditBureauAccountStatus(String creditBureauAccountStatus) {
		this.creditBureauAccountStatus = creditBureauAccountStatus;
	}
		
	
}
