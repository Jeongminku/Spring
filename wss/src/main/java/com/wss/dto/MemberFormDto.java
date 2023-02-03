package com.wss.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.wss.constant.Role;
import com.wss.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//회원가입시 넘겨받을 데이터
public class MemberFormDto {
	
	@NotBlank(message = "아이디를 입력해주세요.")
	@Email(message ="이메일 형식으로 입력해주세요.")
	private String email;
	
	@NotEmpty(message = "비밀번호를 입력해주세요")
	@Length(min=8, max=16, message = "비밀번호는 8자이상, 16자 이하로 해주세요")
	private String password;
	
	private Role role;
	
	@NotBlank(message="닉네임을 입력해주세요")
	private String nickname;
	
	//프사 이미지 파일명
	private String proImgName;
	
	//프사 이미지 주소
	private String proImgUrl;
	
	//프사 원본 이미지명
	private String proImgOri;
	
	
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public static MemberFormDto of(Member member) {
		return modelMapper.map(member, MemberFormDto.class);
	}
}
