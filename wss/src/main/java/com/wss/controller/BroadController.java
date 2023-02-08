package com.wss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wss.service.BroadService;
import com.wss.service.MemberService;

import lombok.RequiredArgsConstructor;

//@Controller
//@RequiredArgsConstructor
//public class BroadController {
//	
//	private final MemberService memberService;
//	private final BroadService broadService;
//	
//	@GetMapping(value = "/broad/{memberId}")
//	public String broadDtl(@PathVariable("memberId") Long memberId, Model model) {
//				
//		
//		return "broadDtl";
//	}
//	
//}
