package com.was.ios.common.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.was.ios.common.dao.DBHelper;

@Service
public class CommonService {

	@Autowired
	DBHelper helper;
	
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);
	
	public boolean signIn(HttpServletRequest request) {
		Object[] params = new Object[5];
		params[0] = request.getParameter("CD_COMPANY").toString();
		params[1] = request.getParameter("ID_USER").toString();
		params[2] = request.getParameter("PW_USER").toString();
		params[3] = request.getParameter("NO_EMP").toString();
		params[4] = request.getParameter("NM_KOR").toString();
		
		return helper.save("USP_LSH_MBI_MA_USER_I", params);
	}
	
	public JSONObject login(HttpServletRequest request) {	
		Object[] params = new Object[3];
		params[0] = request.getParameter("CD_COMPANY").toString();
		params[1] = request.getParameter("ID_USER").toString();
		params[2] = request.getParameter("PW_USER").toString();

		return helper.getObject("USP_LSH_MBI_MA_USER_S", params);
	}
}
