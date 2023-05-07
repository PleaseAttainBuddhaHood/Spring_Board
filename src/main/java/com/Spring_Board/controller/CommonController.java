package com.Spring_Board.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class CommonController 
{
	
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model)
	{
		log.info("접근 제한: " + auth);
		
		model.addAttribute("msg", "접근 제한");
	}
	
	
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model)
	{
		log.info("에러: " + error);
		log.info("로그아웃: " + logout);
		
		
		if(error != null)
		{
			model.addAttribute("error", "당신의 계정에 대한 로그인 에러 점검 ");
		}
		
		
		if(logout != null)
		{
			model.addAttribute("logout", "로그아웃");
		}
	}
	
	
	@GetMapping("/customLogout")
	public void logoutGET()
	{
		log.info("조정된 로그아웃");
	}
}
