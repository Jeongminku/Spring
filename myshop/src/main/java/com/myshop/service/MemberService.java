package com.myshop.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.entity.Member;
import com.myshop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service //service 클래스의 역할을 수행하기 위함입니다.
@Transactional 		//임포트 시 주의하기 org.~~ 으로 해야.  javax 로 하지 않기.
//DB의 트랜잭션과 같은 역할. 이 Service 클래스의 로직을 처리 하다가 에러가 발생하면 로직을 수행하기 이전 상태로 callback (이전상태로 돌려준다)

@RequiredArgsConstructor

public class MemberService implements UserDetailsService { //UserDetailsService: 로그인 시 request에서 넘어온 사용자 정보를 받습니다.
	private final MemberRepository memberRepository; //의존성 주입하기.
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email); //이 사람이 있는지 없는지 부터 확인하기. => 있으면 로그인처리 없으면 안로그인.
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		
		//userDetails의 객체를 반환.
		return User.builder().username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}

	
	
	public Member saveMember(Member member) {
		validateDuplicateMember(member); //이메일 중복체크 메소드 입니다.
		return memberRepository.save(member); //member테이블에 insert 합니다.
	}
	
	//이메일 중복체크 메소드
	private void validateDuplicateMember(Member member) {
		//이메일이 있는지 없는지 select를 먼저 합니다.
		Member findMember = memberRepository.findByEmail(member.getEmail());
		
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}

}