package com.wss.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wss.constant.Role;
import com.wss.dto.BroadFormDto;
import com.wss.dto.MemberFormDto;
import com.wss.entity.Broad;
import com.wss.entity.Member;
import com.wss.service.BroadService;
import com.wss.service.MemberImgService;
import com.wss.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final MemberImgService memberImgService;
	private final PasswordEncoder passwordEncoder;
	private final BroadService broadService;
	
	
	//로그인 화면
	@GetMapping(value="/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	//로그인이 실패했을 때
	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}
	
	//회원가입 화면
	@GetMapping(value="/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";		
	}
	
	//회원가입 버튼 클릭.
	@PostMapping(value="/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BroadFormDto broadFormDto, BindingResult bindingResult, 
			Model model, @RequestParam("profileImg") MultipartFile file) {
		if(bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			Member member1 = memberService.saveMember(member);
			memberImgService.savememberImg(member1, file);
			
			Broad broad = Broad.createBroad(broadFormDto, member);
			
			broadService.saveMember(broad);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		return "redirect:/";
	}
	
	
//	
//	//방송 페이지 보기
//	@GetMapping(value="/broad")
//	public String broadManage() {
//		return "broad/broadMng";
//	}
} 
