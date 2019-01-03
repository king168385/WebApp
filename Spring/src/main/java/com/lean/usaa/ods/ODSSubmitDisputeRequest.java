package com.lean.usaa.ods;

public class ODSSubmitDisputeRequest {
	private String accountNumber;
	private String transactionIdentifier;
	private String inFavorOf;
	private String isGracePeriodEligible;

	public ODSSubmitDisputeRequest() {
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

    public String getIsGracePeriodEligible() {
        return isGracePeriodEligible;
    }

    public void setIsGracePeriodEligible(String isGracePeriodEligible) {
        this.isGracePeriodEligible = isGracePeriodEligible;
    }


}
