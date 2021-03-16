package com.was.ios.common.vo;

import org.json.simple.JSONObject;

public class ResponseObject {
	private static final String Success = "0";
	private static final String Fail = "-1";
	
	public ResponseObject() { }
	
	public JSONObject getResponse(String resultCode, Object obj) {
		JSONObject responseObj = new JSONObject();
		
		responseObj.put("resultCode", resultCode);
		if(resultCode == Fail) {
			//responseObj.put("resultMessage", resultMessage);	
		}
		responseObj.put("result", obj);
		
		return responseObj;
	}

}
