package com.wss.entity;

import javax.persistence.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.wss.constant.Role;
import com.wss.dto.MemberFormDto;

import lombok.*;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {
//회원식별자, 이메일, 이름, 패스워드, 역할(멤버)
//회원식별,이메일,비밀번호,회원등급,닉네임,프사이미지파일명,프사이미지주소
	
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true)
	private String email;
	
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String nickname;
	
	//프사 이미지 파일명
	private String proImgName;
	
	//프사 이미지 주소
	private String proImgUrl;
	
	//프사 원본 이미지명
	private String proImgOri;
	
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setEmail(memberFormDto.getEmail());
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setRole(memberFormDto.getRole());
		member.setNickname(memberFormDto.getNickname());
		member.setProImgName(memberFormDto.getProImgName());
		member.setProImgUrl(memberFormDto.getProImgUrl());
		member.setProImgOri(memberFormDto.getProImgOri());
		
		return member;
	}
}
