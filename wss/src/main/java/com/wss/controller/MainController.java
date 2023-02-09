package com.wss.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wss.constant.Role;
import com.wss.dto.MemberStreamerDto;
import com.wss.entity.Broad;
import com.wss.entity.Member;
import com.wss.repository.BroadRepository;
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
		
//		List<Member> members =  memberService.getMemberBroad(Streamer);
//		model.addAttribute("members", members);
		
		List<MemberStreamerDto> memberStreamerDtoList = memberService.getMemberBroad(Streamer);
		model.addAttribute("members", memberStreamerDtoList);
		
		
		
//		System.out.println("시스아웃시스아웃시스아웃시스아웃시스아웃시스아웃시스아웃시스아웃: " + broadService.getBroad(members.get(0).getId()));
		
//		List<Broad> broads = new ArrayList<Broad>();
//		
//		for(int i=0; i<members.size(); i++  ) {
//			broads.add(broadService.getBroad(members.get(i).getId()));	
//		}
//		System.out.println(broads);
//		model.addAttribute(broads);
		
//		for(int i=0; i<members.size(); i++) {
//			Broad broads = broadService.getBroad(null)
//				
//			model.addAttribute("broads", broads);
//		}
//		
				
				
				//Broad broads = broadService.getBroad(memberid);
		//System.out.println("마이네임이즈 브로즈 : " + broads);
		//model.addAttribute("broads", broads);
		
//		//for문 members를 활용한.
//		for(int i=0; i<members.size(); i++) {
//			Broad broad = broadService.getBroad(members.get(i));
//			model.addAttribute("broad"+i,broad);
//		}
		
		return "main";
	}
	
	@GetMapping(value = {"/view", "/view/{id}"})
	public String dtlpage(@PathVariable("id") Long memberid, Model model) {
		
		//String id = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함 
		try {
						
			
			Member member = memberService.getMember(memberid); //스트리머의 아이디를 통해서 스트리머 member객체를 가져옴.
			//나중에 방송국가져와야함? Broad broad = broadService.~~~
			
			model.addAttribute("member", member);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "게시물 / 사용자를 불러올 수 없습니다.");
			return "main";
		}
		return "/broad/broadDtl";
	}

}
