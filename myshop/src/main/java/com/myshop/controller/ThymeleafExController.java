package com.myshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //컨트롤러의 역할을 하는 클래스를 정의합니다.
@RequestMapping(value = "/thymeleaf") //URL 경로를 지정합니다. http://localhost/thymeleaf
public class ThymeleafExController {
	
	@GetMapping(value = "/ex01") //얘도 URL지정하는것 중 하나. http://localhost/thymeleaf/ex01
	public String thymeleafEx01(Model model) {
		model.addAttribute("data", "타임리프 예제 입니다.");
		return "thymeleafEx/thymeleafEx01";
	}
}
