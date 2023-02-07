package com.wss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wss.constant.Role;
import com.wss.entity.Member;
import com.wss.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BroadService {
	
	@Value("${itemImgLocation}")
	private String imgLocation;
	
	private final MemberService memberService;
	private final MemberRepository memberRepository;
	
	
	public List<Member> getBroad(Role role) {
//		return memberRepository.findByRole(role);
		List<Member> broad = memberRepository.findByRole(role);   //컨트롤러에서 getBroad()사용시 List<Member>를 broad라는 이름으로 다 가져옴.
		return broad;
	}
	
}
