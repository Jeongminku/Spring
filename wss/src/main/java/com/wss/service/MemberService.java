package com.wss.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wss.constant.Role;
import com.wss.dto.BroadFormDto;
import com.wss.dto.MemberFormDto;
import com.wss.dto.MemberStreamerDto;
import com.wss.entity.Broad;
import com.wss.entity.Feed;
import com.wss.entity.Member;
import com.wss.repository.BroadRepository;
import com.wss.repository.FeedRepository;
import com.wss.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	private final MemberRepository memberRepository;
	private final BroadRepository broadRepository;
	private final FeedRepository feedRepository;
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
	

	
	
	public Member saveMember(Member member) {
		validateduplicateMember(member); //이메일 중복체크 먼저.
		return memberRepository.save(member);
	}
	
	
	
	public List<MemberStreamerDto> getMemberBroad(Role role) {
//		return memberRepository.findByRole(role);
		List<Member> MemberList = memberRepository.findByRole(role);   //컨트롤러에서 getBroad()사용시 List<Member>를 broad라는 이름으로 다 가져옴.
		List<MemberStreamerDto> memberStreamerDtoList = new ArrayList<>();
		
		for(Member member : MemberList) {
			Broad broad= broadRepository.findByMemberId(member.getId());
			BroadFormDto broadFormDto = BroadFormDto.of(broad); //broad엔티티를 BroadFormDto로 변경
			
			MemberStreamerDto memberStreamerDto = new MemberStreamerDto(member);
			memberStreamerDto.setBroadFormDto(broadFormDto);
			
			memberStreamerDtoList.add(memberStreamerDto);
		}
		
		return memberStreamerDtoList;
	}
	
	
	public Member getMember(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
	}
	
	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}
	
	
	
	public Long updateMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) throws Exception {
		Member member = memberRepository.findById(memberFormDto.getId())
								.orElseThrow(EntityNotFoundException::new);
		
		member.updateMember(memberFormDto, passwordEncoder);
		return member.getId();
	
	}
	
	public void deleteMember(Long memberId) {
		feedRepository.feedDel(memberId);
		broadRepository.broadDel(memberId);
		memberRepository.memberDel(memberId);

	}
	
	
}
