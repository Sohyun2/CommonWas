package com.was.ios.common.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.was.ios.common.util.Properties;

@Service
public class WebViewService {
	
	@Autowired
	private Properties properties;  
	
	public void uploadFile(HttpServletRequest request) {
		String uploadBasePath = properties.getUploadBasePath();
		String filePath = uploadBasePath + "testFolder";

		// folder 존재 여부 확인 후 존재하지않으면 폴더생성
		File file = new File(filePath);
		if(!file.exists()) {
			file.mkdir();
		}
		
	}
}
