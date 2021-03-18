package com.was.ios.common.vo;

import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMessage {
    private static final long serialVersionUID = 1L;
 
    private static final String DEFAULT_KEY = "result";
    private HttpStatus httpStatus;
    
    private int code;
    private boolean status;
    private String message;
    private JSONObject data;
    private ErrorMessage error;
 
    public ResponseMessage() {
    	this(HttpStatus.OK);
    }
    
    public ResponseMessage(HttpStatus httpStatus) {
    	this.httpStatus = httpStatus;
    	
        this.code = this.httpStatus.value();
        this.status = (this.httpStatus.isError())? false:true;
        this.message = this.httpStatus.getReasonPhrase();
        this.data = new JSONObject();
    }
 
    public ResponseMessage(AbstractEbloBaseException ex, String referedUrl) {
    	this.httpStatus = ex.getHttpStatus();

        this.code = this.httpStatus.value();
        this.status = (this.httpStatus.isError())? false:true;
        this.message = this.httpStatus.getReasonPhrase();
        this.data = new JSONObject();
        
        this.error = new ErrorMessage(code, ex.getMessage(), referedUrl);
    }
    
    public ResponseMessage(HttpStatus httpStatus, Object result) {
        this(httpStatus);
        this.data.put(DEFAULT_KEY, result);
    }
    
    public void add(String key, Object result) {
        this.data.put(key, result);
    }
    
    public void remove(String key) {
        this.data.remove(key);
    }

    public ResponseEntity<JSONObject> getResponse(HttpStatus httpStatus) {
    	JSONObject result = new JSONObject();
    	
    	result.put("code", this.code);
    	result.put("status", this.status);
    	result.put("data", this.data);
    	result.put("message", this.message);
    	result.put("error", this.error);
    	
    	return new ResponseEntity<JSONObject>(result, this.httpStatus);
    }
    
    public ResponseEntity<JSONObject> getResponse() {
    	JSONObject result = new JSONObject();
    	
    	result.put("code", this.code);
    	result.put("status", this.status);
    	result.put("data", this.data);
    	result.put("message", this.message);
    	result.put("error", this.error);
    	
    	return new ResponseEntity<JSONObject>(result, this.httpStatus);
    }
}
