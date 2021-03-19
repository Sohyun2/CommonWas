package com.was.ios.common.util.response;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

public class DataBaseResponse {
	private boolean result;
	private String errCode;
	private HttpStatus httpStatus;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
		convertResultToHttpStatus();
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	private void convertResultToHttpStatus() {
		if(this.result) {
			this.httpStatus = HttpStatus.OK;
		} else {
			if(this.errCode.equals("401")) {
				this.httpStatus = HttpStatus.UNAUTHORIZED; // 인증실패
			} else if (this.errCode.equals("403")) {
				this.httpStatus = HttpStatus.FORBIDDEN; // 인가 실패(사용자가 리소스에 대한 필요 권한을 갖고있지 않음)
			} else if (this.errCode.equals("403")) {
				this.httpStatus = HttpStatus.FORBIDDEN; // 인가 실패(사용자가 리소스에 대한 필요 권한을 갖고있지 않음)
			} 
		}
	}
	
	public JSONObject sendDBResult() {
		JSONObject resultObj = new JSONObject();
		
		resultObj.put("status", this.httpStatus);
		
		if(this.httpStatus != HttpStatus.OK) {
			resultObj.put("errMsg", this.errCode);
		}
		
		return resultObj;
	}
}
