package com.truechain.task.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.truechain.task.api.service.UserService;

@RequestMapping("/wx")
@Controller
public class WxController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取微信的access_token
	 */
	@GetMapping("/getWxUserInfo")
	public String getWxUserInfo(HttpServletRequest httpServletRequest,String code, String state) {
		try {
			userService.getWxUserInfo(code, Long.valueOf(state));
		} catch (Exception e) {
			return "redirect:"+httpServletRequest.getContextPath()+"/#/invite-reg-success";
		}
		return "redirect:"+httpServletRequest.getContextPath()+"/#/mine";
	}

}
