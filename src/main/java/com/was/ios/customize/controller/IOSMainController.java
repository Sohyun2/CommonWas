package com.was.ios.customize.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.was.ios.common.dao.DBHelper;

@RestController
public class IOSMainController {
 
	@Autowired
	DBHelper helper;
	
	private static final Logger logger = LoggerFactory.getLogger(IOSMainController.class);
	
	@GetMapping(value = "/get")
	public String testGet() {
		logger.info("===== get method.. =====");
				
		JSONObject obj = new JSONObject();
		obj.put("key", "value");
		
		// json형식을 문자열로 변환(전송하기위해서..)
		String jsonStr = obj.toString();
		
		return jsonStr;
	}

	@PostMapping(value="/post")
	public String testPost(HttpServletRequest request) {
		logger.info("===== post method.. =====");
		
		String name = request.getParameter("name");
		logger.info(name);
		
		JSONObject obj = new JSONObject();
		obj.put("key", name);
		
		// json형식을 문자열로 변환(전송하기위해서..)
		String jsonStr = obj.toString();
		
		return jsonStr;
	}
}
