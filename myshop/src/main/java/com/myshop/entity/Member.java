package com.myshop.entity;

import javax.persistence.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.myshop.constant.Role;
import com.myshop.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 				//이것을 Entity클래스로 사용할 것이라고 어노테이션합니다.
@Table(name="member") 	//테이블명을 설정합니다.
@Getter 				//Getter,Setter,ToString 롬복을 사용합니다.
@Setter
@ToString
public class Member extends BaseEntity{
	
	@Id
	@Column(name="member_id") //컬럼의 이름을 따로 지정.
	@GeneratedValue(strategy = GenerationType.AUTO) //PK 생성 전략은 AUTO
	private Long id; //PK가 되줄 id
	
	private String name;
	
	@Column(unique = true) //유니크 컬럼 생성
	private String email;
	
	private String password;
	
	private String address;
	
	@Enumerated(EnumType.STRING) //열거형 타입을 지정하면 USER아니면 ADMIN만 저장이 가능하다. (그렇게 enum에 지정해놨기 때문.)
	private Role role; //Enum 클래스 Role
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setAddress(memberFormDto.getAddress());
		
		//비밀번호 같은 경우에는 암호화를 시켜서 저장시켜야 하기때문에 이 과정이 필요합니다.
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		
		member.setRole(Role.USER);
		
		return member;
	}
	
	
}
