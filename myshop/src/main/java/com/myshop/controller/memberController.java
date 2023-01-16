package com.myshop.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myshop.dto.MemberFormDto;
import com.myshop.entity.Member;
import com.myshop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members") //경로잡아주기.
@Controller
@RequiredArgsConstructor
public class memberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping(value = "/new") //get방식으로 받을 때 사용할 것 입니다.
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	//회원가입 버튼을 눌렀을 때 실행되는 메소드
	@PostMapping(value ="/new") //post방식으로 받을 때 사용할 것 입니다.
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		//@Valid : 유효성을 검증하려는 객체 앞에 붙여줍니다.
		//BindingResult 는 유효성 검증 후에 결과를 담아주는 곳입니다. (유효성 검증 결과를 BindingResult에 넣어줍니다)

		//에러가 있다면 회원가입 페이지로 이동
		if(bindingResult.hasErrors()) {	//하나라도 에러가 발견되면 true
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);			
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage()); //memberForm.html의 [[${errorMessage}]] 에 담길 예정.
			return "member/memberForm";
		}
				
		return "redirect:/"; //    '/'이거 경로로 redirect를 자동으로 해줄것 (/ 는 루트경로로써, 그냥 localhost로 보내버림.)
	}
	
}
