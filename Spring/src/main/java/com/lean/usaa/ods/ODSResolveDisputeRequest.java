package com.lean.usaa.ods;

public class ODSResolveDisputeRequest {
	private String accountNumber;
	private String transactionIdentifier;
	private String inFavorOf;
	
	public ODSResolveDisputeRequest() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    public String getInFavorOf() {
        return inFavorOf;
    }

    public void setInFavorOf(String inFavorOf) {
        this.inFavorOf = inFavorOf;
    }


	
}
