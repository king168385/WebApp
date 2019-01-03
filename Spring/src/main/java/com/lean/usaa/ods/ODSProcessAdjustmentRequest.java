package com.lean.usaa.ods;

public class ODSProcessAdjustmentRequest {
	private String accountNumber;
	private String promotionalIdentifier;
	private String transactionIdentifier;
	private String adjustmentAmount;
	private String adjustmentCode;

	public ODSProcessAdjustmentRequest() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPromotionalIdentifier() {
        return promotionalIdentifier;
    }

    public void setPromotionalIdentifier(String promotionalIdentifier) {
        this.promotionalIdentifier = promotionalIdentifier;
    }

    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    public String getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(String adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public String getAdjustmentCode() {
        return adjustmentCode;
    }

    public void setAdjustmentCode(String adjustmentCode) {
        this.adjustmentCode = adjustmentCode;
    }
	
	
}
