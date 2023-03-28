package com.wss.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wss.entity.Broad;
import com.wss.entity.Feed;
import com.wss.entity.Member;
import com.wss.service.BroadService;
import com.wss.service.FeedService;
import com.wss.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BroadController {
	private final MemberService memberService;
	private final BroadService broadService;
		
	@GetMapping(value = "/view/{id}/media")
	public String dtlpage(@PathVariable("id") Long memberid, Model model) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함 
		try {
			//로그인한 멤버의 정보가 필요할때 데려올 setmember
			Member setmember = memberService.findByEmail(email);
			model.addAttribute("setmember", setmember);
			
			
			Member member = memberService.getMember(memberid); //스트리머의 아이디를 통해서 스트리머 member객체를 가져옴.
			model.addAttribute("member", member);
			
			
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "미디어를 불러올 수 없습니다.");
			return "main";
		}
		return "/broad/broadMedia";
	}
}
