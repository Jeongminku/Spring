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
	
	//닉네임
	private String nickname;
	
	//프사 이미지 파일명
	@Column(name ="img_name")
	private String imgName;
	
	//프사 이미지 주소
	@Column(name ="img_url")
	private String imgUrl;
	
	//프사 원본 이미지명
	@Column(name ="img_ori")
	private String imgOri;
	
	//방송정보(스트리머 only)
	@Column(name ="broad_info")
	private String broadInfo;
	
	//방송카테고리(스트리머 only)
	private String category;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setEmail(memberFormDto.getEmail());
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setRole(memberFormDto.getRole());
		member.setNickname(memberFormDto.getNickname());
		
		member.setBroadInfo(memberFormDto.getBroadInfo());
		
		return member;
	}
	
	public void updateImg(String imgName, String imgUrl, String imgOri) {
		this.imgName = imgName;
		this.imgUrl = imgUrl;
		this.imgOri = imgOri;
	}
}
