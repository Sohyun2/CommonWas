package com.was.ios.common.controller;

import java.net.InetAddress;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.was.ios.common.service.WebViewService;

@Controller
public class WebViewController {

	private static final Logger logger = LoggerFactory.getLogger(WebViewController.class);
	
	private static final String MAIN_VIEW = "common/";
	private static final String CUSTOMIZE_VIEW = "customize/webview/";
	
	private static final String ENTRY_POINT = "/";
	private static final String MAIN_URL = "/{url}";
	private static final String CUSTOMIZE_URL = "/{url}/{actionName}";
	
	@Autowired
	private WebViewService service;
	
	@GetMapping(value = ENTRY_POINT)
	public String home(Locale locale, Model model) {
		try {
			InetAddress local;
			local = InetAddress.getLocalHost();
			String ip = local.getHostAddress();
			logger.info("===== " + ip + " server start =====");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return MAIN_VIEW + "main";
	}
	
	@GetMapping(value = MAIN_URL)
	public ModelAndView test(HttpServletRequest request,
			@PathVariable("url") String url) {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName(CUSTOMIZE_VIEW + url);
		
		return mv;
	}

	@ResponseBody
	@PostMapping(value = CUSTOMIZE_URL)
	public String test(HttpServletRequest request, @PathVariable("url") String url, @PathVariable("actionName") String actionName) {
		if(actionName.equals("file")) {
			service.uploadFile(request);
		}
		
		return "return";
	}
}
