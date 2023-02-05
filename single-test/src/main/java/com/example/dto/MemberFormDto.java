package com.example.dto;

import javax.validation.constraints.*;

import com.example.constant.Role;

import lombok.*;

@Getter
@Setter
public class MemberFormDto {

	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	private String name; //닉네임
	
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	@Email(message ="이메일 형식으로 입력해주세요.")
	private String email; //아이디로 쓸 이메일
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	private String password; // 비밀번호
	
	
}
