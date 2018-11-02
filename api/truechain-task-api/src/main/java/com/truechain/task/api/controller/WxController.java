package com.truechain.task.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.truechain.task.api.service.UserService;

@RequestMapping("/wx")
@Controller
public class WxController {
	
	@Value("${app.domain}")
	private String domain;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取微信的access_token
	 */
	@GetMapping("/getWxUserInfo")
	public String getWxUserInfo(String code, String state) {
		try {
			userService.getWxUserInfo(code, Long.valueOf(state));
		} catch (Exception e) {
			return "redirect:http://"+domain+"/#/invite-reg-success";
		}
		return "redirect:http://"+domain+"/#/mine";
	}

}
