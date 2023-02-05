package com.example.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.MemberFormDto;
import com.example.entity.Member;
import com.example.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberContorller {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	//회원가입 화면
	@GetMapping(value="/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	//회원가입화면에 들어와서 회원가입시 실행됨.
	@PostMapping(value="/new")
	public String memberForm(MemberFormDto memberFormDto) {
		Member member = Member.createMember(memberFormDto, passwordEncoder);
		memberService.saveMember(member);
		return "redirect:/";
	}
	
}
