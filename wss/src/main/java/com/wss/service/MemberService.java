package com.wss.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wss.constant.Role;
import com.wss.entity.Member;
import com.wss.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email);
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}
	

	//이메일 중복체크 메소드
	private void validateduplicateMember(Member member) {
		//이메일이 있는지 없는지 select를 먼저 합니다.
		Member findMember = memberRepository.findByEmail(member.getEmail());
		
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원 입니다.");
		}
	}
	

	public List<Member> getMemberBroad(Role role) {
//		return memberRepository.findByRole(role);
		List<Member> broad = memberRepository.findByRole(role);   //컨트롤러에서 getBroad()사용시 List<Member>를 broad라는 이름으로 다 가져옴.
		return broad;
	}
	
	
	public Member saveMember(Member member) {
		validateduplicateMember(member); //이메일 중복체크 먼저.
		return memberRepository.save(member);
	}
	

}
