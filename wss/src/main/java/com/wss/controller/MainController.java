package com.wss.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wss.constant.Role;
import com.wss.entity.Member;
import com.wss.service.BroadService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final BroadService broadService;
	
	
	@GetMapping(value = "/")
	public String main(Model model) {
		
		Role Streamer = Role.STREAMER;
		
		List<Member> broads =  broadService.getBroad(Streamer);
		model.addAttribute("broads", broads);
		
		return "main";
	}

}
