package com.myshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.myshop.service.MemberService;

@Configuration //스프링에서 설정 클래스로 사용하겠다는 어노테이션 입니다.
@EnableWebSecurity //springSecurityFilterChain이 자동으로 포함됩니다.
public class SecurityConfig{

	@Autowired
	MemberService memberService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//로그인에 대한 설정
		http.formLogin()
			.loginPage("/members/login") //로그인 페이지 url을 설정합니다.
			.defaultSuccessUrl("/") //로그인 성공시 이동할 페이지의 url 입니다.
			.usernameParameter("email") //로그인 시 사용할 파라메터의 이름입니다.
			.failureForwardUrl("/members/login") //로그인 실패시 이동할 페이지의 url입니다.
			.and() //로그인 이후에 로그아웃을 하기 위한 규칙 이라고 생각하기.
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 url 입니다.  ,  AntPathRequestMatcher(url주소) 
			.logoutSuccessUrl("/"); //로그아웃 성공시 url 입니다.
		
		return http.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() { //비밀번호 암호화를 위해서 사용하는 빈(bean)
		return new BCryptPasswordEncoder();
	}
}
