package com.wss.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class AlertMethod {

	public static String redirectAfterAlert (String alertMsg, String location, HttpServletResponse resp) {
		try {
			// HttpServletReposnse 객체의 ContentType을 설정.
			resp.setContentType("text/html; charset=UTF-8");
			
			// PrintWriter 객체 생성
			PrintWriter out;
			out = resp.getWriter();
			
			// alert 창을 띄우는 자바스크립트 코드 출력
			out.println("<script>alert('" + alertMsg + "'); location.href= '" + location + "'</script>");
			// 출력 버퍼에 있는 모든 내용을 출력하고, 버퍼를 비운다.
			out.flush();
		}catch (IOException e) {
			//IOException(입출력동작중 예외) 발생한 경우 스택트레이스 출력.
			e.printStackTrace();
		}
		return null;
	}
	
}



