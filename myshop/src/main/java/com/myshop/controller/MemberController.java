package com.myshop.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myshop.dto.MemberFormDto;
import com.myshop.entity.Member;
import com.myshop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members") //경로잡아주기.
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping(value = "/new") //get방식으로 받을 때 사용할 것 입니다.
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	//회원가입 버튼을 눌렀을 때 실행되는 메소드
	@PostMapping(value ="/new")  //post방식으로 받을 때 사용할 것 입니다.
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
	
	//로그인 화면
	@GetMapping(value = "/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	
	
	private final SessionManager sessionManager;

/*
	//쿠키, 세션 테스트
	@PostMapping(value = "/login2")
	public String loginMember2(HttpServletResponse response, HttpSession session, @RequestParam String email) {
		System.out.println("email: " + email);
		Cookie idCookie = new Cookie("userCookie", email); //쿠키는 내 LOCAL에 저장을하기위해 생성 합니다. Cookie("Key", value)
		response.addCookie(idCookie); //쿠키에 저장합니다.
		System.out.println("userCookie: "+ idCookie);
		
		
//		session.setAttribute("useSessionId", email); //setAttribute("key", value)를 통해서 session객체에 저장해줍니다.
													//이후에는 private final Sessionmanager 를 작성하겠습니다.
									//createSession이라는 다른 사람이 만들어둔 코드를 사용할 예정이라서 얘는 주석처리 되었습니다.
		
		sessionManager.createSession(email, response);
		
		return "member/memberLoginForm";
	}
*/	
	
	
	//로그인이 실패했을 때
	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}
	
}
