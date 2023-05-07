package com.Spring_Board.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler 
{
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, 
					   AccessDeniedException accessException) throws IOException, ServletException
	{
		log.error("접근 거절 핸들러");
		log.error("재조정 중...");
				
		response.sendRedirect("/accessError");
	}
}
