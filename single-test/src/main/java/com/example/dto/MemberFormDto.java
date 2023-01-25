package com.example.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	@NotBlank(message="이름은 필수 입력 값입니다.")
	private String name;
	@NotBlank(message="이메일은 필수 입력 값입니다.")
	@Email
	private String email;
	@NotBlank(message="비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message="비밀번호는 8자이상 16자 이하입니다")
	private String password;
	@NotBlank(message="주소는 필수 입력 값입니다.")
	private String address;
}
