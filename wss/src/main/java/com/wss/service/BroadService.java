package com.wss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wss.constant.Role;
import com.wss.entity.Broad;
import com.wss.entity.Member;
import com.wss.repository.BroadRepository;
import com.wss.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BroadService {
	
	private final MemberRepository memberRepository;
	private	final BroadRepository broadRepository;

	public Broad getBroad(Member id) {
		Broad broad = broadRepository.findByMemberId(id);   //컨트롤러에서 getBroad()사용시 List<Member>를 broad라는 이름으로 다 가져옴.
		return broad;
	}
	
	public Broad saveMember(Broad broad) {
		return broadRepository.save(broad);
	}
	

	@Value("${itemImgLocation}")
	private String imgLocation;
	
}
