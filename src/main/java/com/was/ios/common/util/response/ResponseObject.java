package com.was.ios.common.util.response;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseObject {

	private HttpStatus httpStatus;
	
    private int code;
    private boolean status;
    private String message;
    private JSONObject data;
    private String errMsg;
    
    public ResponseObject() { }

    public ResponseObject(HttpStatus httpStatus) { 
    	setStatus(httpStatus);
    }

    public void setStatus(HttpStatus httpStatus) {
    	this.httpStatus = httpStatus;

    	this.code = httpStatus.value();
    	this.status = (httpStatus.isError()) ? false : true;
    	this.data = new JSONObject();
    	this.message = httpStatus.getReasonPhrase();
    }
    
    public void setStatus(HttpStatus httpStatus, String errMsg) {
    	this.httpStatus = httpStatus;

    	this.code = httpStatus.value();
    	this.status = (httpStatus.isError()) ? false : true;
    	this.data = new JSONObject();
    	this.message = httpStatus.getReasonPhrase();
    	
    	if(!errMsg.equals("")) {
    		this.errMsg = errMsg;
    	}
    }
    public void add(String key, Object result) throws NullPointerException {	
    	if(this.httpStatus == null) {
    		throw new NullPointerException();
    	}
        this.data.put(key, result);
    }
    
    public void remove(String key) throws NullPointerException {	
    	if(this.httpStatus == null) {
    		throw new NullPointerException();
    	}
        this.data.remove(key);
    }

    public ResponseEntity<JSONObject> sendResponse() throws NullPointerException {	
    	if(this.httpStatus == null) {
    		throw new NullPointerException();
    	}
    	return new ResponseEntity<JSONObject>(setResponse(), this.httpStatus);
    }
    
    public ResponseEntity<JSONObject> sendResponse(HttpStatus httpStatus) throws NullPointerException {	
    	if(this.httpStatus == null) {
    		throw new NullPointerException();
    	}
    	return new ResponseEntity<JSONObject>(setResponse(), httpStatus);
    }
    
    private JSONObject setResponse() {
    	JSONObject resultObj = new JSONObject();
    	
    	resultObj.put("code", this.code);
    	resultObj.put("status", this.status);
    	resultObj.put("data", this.data);
    	resultObj.put("message", this.message);
    	
    	if(!this.status) {
    		resultObj.put("errMsg", this.errMsg);
    	}
    	
    	return resultObj;    	
    }
}
