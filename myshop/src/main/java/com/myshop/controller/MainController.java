package com.myshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping(value = "/")
	public String main() {
		return "main"; //아직 안만들었지만 곧 만들 예정.
	}
}
