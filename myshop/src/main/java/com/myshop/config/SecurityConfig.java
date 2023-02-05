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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	MemberService memberService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//로그인에 대한 설정
		http.formLogin()
			.loginPage("/members/login") //로그인 페이지 url을 설정합니다.
			.usernameParameter("email") //로그인 시 사용할 파라메터의 이름입니다.
			.defaultSuccessUrl("/") //로그인 성공시 이동할 페이지의 url 입니다.
			.failureUrl("/members/login/error")//로그인 실패시 이동할 페이지의 url입니다.
			.and() //로그인 이후에 로그아웃을 하기 위한 규칙 이라고 생각하기.
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 url 입니다.  ,  AntPathRequestMatcher(url주소) 
			.logoutSuccessUrl("/"); //로그아웃 성공시 url 입니다.
		
		//페이지의 접근에 관한 설정
			http.authorizeHttpRequests()
				.mvcMatchers("/css/**","/js/**","/img/**").permitAll()
				.mvcMatchers("/","/members/**","/item/**","/images/**").permitAll() //모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정합니다. .mvcMatchers(페이지 경로).permitAll()
				.mvcMatchers("/admin/**").hasRole("ADMIN") 
				//'/admin'으로 시작하는 경로는 계정이 ADMIN role일 경우에만 접근이 가능하도록 설정합니다.
				.anyRequest().authenticated(); 
				//그 외의 페이지는 모두 로그인(인증)을 받아야 합니다.
	 			
				//인증되지 않은 사용자가 리소스(페이지, 이미지 등)에 접근했을 때 설정 (이거 하기전에 클래스 하나 추가생성하겠습니다. config에 가서 CustomAuthenticationEntryPoint 클래스를 만들겠습니다.)
			http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
			
			
			return http.build();
		}
		@Bean
		public PasswordEncoder passwordEncoder() { //비밀번호 암호화를 위해서 사용하는 빈(bean)
			return new BCryptPasswordEncoder();
		}
	}
