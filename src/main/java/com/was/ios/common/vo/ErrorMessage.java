package com.was.ios.common.vo;

public class ErrorMessage {

    private static final long serialVersionUID = 1L;
 
    private int code;
    private String errorMessage;
    private String referedUrl;
    
    public ErrorMessage(int code, String errorMessage, String referedUrl) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.referedUrl = referedUrl;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getReferedUrl() {
		return referedUrl;
	}

	public void setReferedUrl(String referedUrl) {
		this.referedUrl = referedUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
