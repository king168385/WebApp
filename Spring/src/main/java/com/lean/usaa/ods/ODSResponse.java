package com.lean.usaa.ods;

public class ODSResponse {
	private String status;
	private String message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    public ODSResponse(String status, String message)
    {
        super();
        this.status = status;
        this.message = message;
    }
	
	
}
