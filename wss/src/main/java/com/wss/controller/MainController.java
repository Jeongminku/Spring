package com.wss.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wss.constant.Role;
import com.wss.entity.Broad;
import com.wss.entity.Member;
import com.wss.service.BroadService;
import com.wss.service.MemberService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final BroadService broadService;
	private final MemberService memberService;
	
	@GetMapping(value = "/")
	public String main(Model model) {
		
		Role Streamer = Role.STREAMER;
		
		List<Member> members =  memberService.getMemberBroad(Streamer);
		model.addAttribute("members", members);
		
//		//for문 members를 활용한.
//		for(int i=0; i<members.size(); i++) {
//			Broad broad = broadService.getBroad(members.get(i));
//			model.addAttribute("broad"+i,broad);
//		}
		
		return "main";
	}

}
