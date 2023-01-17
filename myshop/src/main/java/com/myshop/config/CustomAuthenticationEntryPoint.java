package com.myshop.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

//﻿인증되지 않은 사용자가 리소스를 요청할 경우 어떻게 처리할지에 대한 클래스 입니다.
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); //sendError(int형, 문자열)
								//SC_UNAUTHORIZED: int javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED : 401에러 [0x191]
								//관련검색 : http 상태 코드.
	
			
	}	
	
}
