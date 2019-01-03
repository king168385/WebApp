package com.lean.usaa.ods;

public class ODSClearDisputeRequest {
	private String chargebackAmount;
	private String chargebackActionCode;
	private String accountNumber;
	private String sequenceNumber;
	private String transactionIdentifier;
	private String messageText;
	
	public ODSClearDisputeRequest() {
    }

	public String getChargebackAmount() {
		return chargebackAmount;
	}

	public void setChargebackAmount(String chargebackAmount) {
		this.chargebackAmount = chargebackAmount;
	}

	public String getChargebackActionCode() {
		return chargebackActionCode;
	}

	public void setChargebackActionCode(String chargebackActionCode) {
		this.chargebackActionCode = chargebackActionCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    public String getMessageText()
    {
        return messageText;
    }

    public void setMessageText(String messageText)
    {
        this.messageText = messageText;
    }
	
}
