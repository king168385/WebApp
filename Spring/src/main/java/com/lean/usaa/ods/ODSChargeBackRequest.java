package com.lean.usaa.ods;

public class ODSChargeBackRequest {
    private String accountNumber;
    private String transactionIdentifier;
    private String transactionType;
    private String msgReasonCode ;
    private String chargeBackAmount;
    private String chargeBackTypeCode;
    private String chargeBackMsgCode;
    private String excludeTranIdCode;
    private String messageText;
    
    
    public ODSChargeBackRequest() {
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


    public String getTransactionType() {
        return transactionType;
    }


    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }



    public String getMsgReasonCode() {
		return msgReasonCode;
	}


	public void setMsgReasonCode(String msgReasonCode) {
		this.msgReasonCode = msgReasonCode;
	}


	public String getChargeBackAmount() {
        return chargeBackAmount;
    }


    public void setChargeBackAmount(String chargeBackAmount) {
        this.chargeBackAmount = chargeBackAmount;
    }


    public String getChargeBackTypeCode() {
        return chargeBackTypeCode;
    }


    public void setChargeBackTypeCode(String chargeBackTypeCode) {
        this.chargeBackTypeCode = chargeBackTypeCode;
    }


    public String getChargeBackMsgCode() {
        return chargeBackMsgCode;
    }


    public void setChargeBackMsgCode(String chargeBackMsgCode) {
        this.chargeBackMsgCode = chargeBackMsgCode;
    }


    public String getExcludeTranIdCode() {
        return excludeTranIdCode;
    }


    public void setExcludeTranIdCode(String excludeTranIdCode) {
        this.excludeTranIdCode = excludeTranIdCode;
    }


    public String getMessageText() {
        return messageText;
    }


    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    
    
    
}
