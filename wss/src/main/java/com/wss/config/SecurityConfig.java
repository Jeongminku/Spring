package com.wss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.wss.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	MemberService memberService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.formLogin()
			.loginPage("/members/login")
			.usernameParameter("email")
			.defaultSuccessUrl("/")
			.failureUrl("/members/login/error")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
			.logoutSuccessUrl("/");
		
//		http.csrf().disable();
		
		http.authorizeHttpRequests()
		.mvcMatchers("/css/**","/js/**","/img/**", "/fonts/**").permitAll()
		.mvcMatchers("/","/members/**","/item/**","/images/**","/favicon.ico").permitAll()
		.anyRequest().authenticated();
		
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		
		
		return http.build();
	}
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
