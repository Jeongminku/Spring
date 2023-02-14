package com.wss.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wss.constant.Role;
import com.wss.dto.BroadFormDto;
import com.wss.dto.MemberFormDto;
import com.wss.entity.Broad;
import com.wss.entity.Feed;
import com.wss.entity.Member;
import com.wss.service.BroadService;
import com.wss.service.FeedService;
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
	private final FeedService feedService;
	
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
	
	
	//멤버 수정 페이지 보기
	@GetMapping(value = {"/setting", "/setting/{id}"})
	public String memberDtl(@PathVariable("id") Long memberId, Model model) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함 
		try {
			//세팅폼 자체에 memberFormDto 가 있으므로 기본 설정을 해줘야 접근이 가능하다.
			MemberFormDto memberFormDto = new MemberFormDto();
			model.addAttribute(memberFormDto);
			
			Member member = memberService.findByEmail(email);
			Member member1 = memberService.getMember(member.getId());
			model.addAttribute("setmember",member1);
			System.out.println(member1.getNickname());
		} catch (EntityNotFoundException e) {
			
		}
		
		return "member/memberSettingForm";
	}
	
	//멤버 닉네임,패스워드,이미지업로드를 수정
		@PostMapping(value = {"/setting", "/setting/{id}"})
		public String memberUpdate(@Valid MemberFormDto memberFormDto, BroadFormDto broadFormDto, BindingResult bindingResult, 
				Model model, @RequestParam("profileImg") MultipartFile file) {
			
			if(bindingResult.hasErrors()) {
				return "redirect:/";
			}
			
			try {
				Long id = memberService.updateMember(memberFormDto, passwordEncoder);
				Member member = memberService.getMember(id);
				memberImgService.updateMemberImg(member, file);

			}catch (Exception e) {
				model.addAttribute("errorMessage", "멤버 수정 중 에러가 발생하였습니다.");
				return "redirect:/";
			}
			return "redirect:/";
		}	
	
	//회원 탈퇴
		@GetMapping(value = "/deleteMember/{id}")
		public String deleteMember(@PathVariable("id") Long memberid) {
			memberService.deleteMember(memberid);
			SecurityContextHolder.clearContext();
			return "redirect:/";
		}
	
} 
