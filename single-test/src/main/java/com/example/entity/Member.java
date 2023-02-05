package com.example.entity;

import javax.persistence.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.constant.Role;
import com.example.dto.MemberFormDto;

import lombok.*;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="member_id")
	private Long id; //자동생성PK
	
	private String name; //닉네임
	
	private String email; //아이디로 쓸 이메일
	
	private String password; // 비밀번호
	
	@Enumerated(EnumType.STRING)
	private Role role; // 역할군
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		
		member.setRole(Role.STREAMER);
		return member;
	}
}
