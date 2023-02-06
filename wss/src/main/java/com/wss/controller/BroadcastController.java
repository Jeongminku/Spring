//package com.wss.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.wss.dto.BroadcastFormDto;
//import com.wss.entity.Member;
//
//@Controller
//public class BroadcastController {
//	@GetMapping(value="/broadcast/new")
//	
//	public String broadcastForm(Model model) {
//		model.addAttribute("broadcastFormDto",new BroadcastFormDto());
//		
//		return "broadcast/broadcastForm";
//	}
//	
//
//}
