package com.was.ios.common.controller;

import java.net.InetAddress;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.was.ios.common.model.DBHelper;
import com.was.ios.common.security.JwtAuthenticationTokenProvider;
import com.was.ios.common.service.CommonService;
import com.was.ios.common.vo.ResponseObject;

@RestController
public class CommonController {

	private static final String Success = "0";
	private static final String Fail = "-1";

	@Autowired
	private DBHelper helper;
	@Autowired
	private CommonService service;
	@Autowired
	private JwtAuthenticationTokenProvider tokenProvider;

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@GetMapping(value = "/")
	public String home(Locale locale, Model model) {
		try {
			InetAddress local;
			local = InetAddress.getLocalHost();
			String ip = local.getHostAddress();
			logger.info("===== " + ip + " server start =====");

			// db connection 연결
			helper.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "main";
	}

	@PostMapping(value = "/signIn")
	public String signIn(HttpServletRequest request) {
		// 비밀번호 암호화 시킬 것
		boolean signInResult = service.signIn(request);

		if (!signInResult)
			return "fail";
		return "success";
	}

	@PostMapping(value = "/login")
	public ResponseEntity<JSONObject> login(HttpServletRequest request) {
		//1. db에서 login값 확인 2. login정보 확인되면 토큰 발급해서 클라이언트단으로 보내주기
		JSONObject loginObject = service.login(request); // db에 넘어온 정보가 존재하는지 확인

		ResponseObject response = new ResponseObject();
		
		// 일치하는 login정보가 없음
		if (loginObject == null) {
			return response.sendResponse(HttpStatus.UNAUTHORIZED);
		}

		response.setStatus(HttpStatus.OK);
		response.add("token", getToken(loginObject)); // login정보가 존재하면 token발급
		response.add("loginObject", loginObject);
		
		return response.sendResponse();
	}

	private String getToken(JSONObject jsonObject) {
		return tokenProvider.createToken(jsonObject);
	}

	private JSONObject decodingToken(String token) {
		return tokenProvider.decodingToken(token);
	}

	private boolean verifyToken(String token) {
		JSONObject jsonObj = this.decodingToken(token);
		if (jsonObj == null)
			return false;

		return true;
	}
}
