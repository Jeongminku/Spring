package com.wss.service;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wss.constant.Role;
import com.wss.dto.BroadFormDto;
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
	
	public Broad saveMember(Broad broad) {
		return broadRepository.save(broad);
	}
	
	public Broad findById(Long id) {
		return broadRepository.findByMemberId(id);
	}
	
	@Value("${itemImgLocation}")
	private String imgLocation;
	
}
