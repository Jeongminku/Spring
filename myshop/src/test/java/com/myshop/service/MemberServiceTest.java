package com.myshop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.constant.Role;
import com.myshop.dto.MemberFormDto;
import com.myshop.entity.Member;

@SpringBootTest
@Transactional //테스트 실행 후 롤백처리합니다.
@TestPropertySource(locations="classpath:application-test.properties")
class MemberServiceTest {

	@Autowired //의존성 주입
	MemberService memberService;
	
	@Autowired //의존성 주입
	PasswordEncoder passwordEncoder;

	public Member createMember() {
		//내용은 Member 엔티티에서 복사해왔습니다.
		MemberFormDto member = new MemberFormDto();
		member.setName("가라데이터");
		member.setEmail("gara@email.com");
		member.setAddress("서울시 가라구 구라동");
		//테스트이기때문에 암호화 하지 않았습니다.
		member.setPassword("1234");
		
		return Member.createMember(member, passwordEncoder);
	}
	
	@Test
	@DisplayName("회원가입 테스트")
	public void saveMemerTest() {
		Member member = createMember(); //위에서 만든 멤버객체를 그대로 받음.
		Member savedmember = memberService.saveMember(member); //insert
		//멤버서비스에서 savemember로 호출합니다. memberservice는 위에서 autowired했음.
		//멤버테이블의 멤버 엔티티 값들이 savedmember과정에서 insert가 됩니다(?)
		
		assertEquals(member.getEmail(), savedmember.getEmail()); 
		//테스트코드를 사용할 때 값을 비교하는 역할을 합니다.
		//내가 넣어준 값(member)과 멤버서비스에 insert된 값(savedmember)이 같은지 확인합니다.
		//저장하려고 했던 값과 실제 저장된 데이터를 비교합니다.
		assertEquals(member.getName(), savedmember.getName());
		assertEquals(member.getAddress(), savedmember.getAddress());
		assertEquals(member.getPassword(), savedmember.getPassword());
		assertEquals(member.getRole(), savedmember.getRole());
		
	}
	
	@Test
	@DisplayName("중복 회원 가입 테스트")
	public void saveDuplicateMemberTest() {
		Member member1 = createMember();
		Member member2 = createMember();
		
		memberService.saveMember(member1);
	
		//예외처리 테스트
		Throwable e = assertThrows(IllegalStateException.class, ()-> {
			memberService.saveMember(member2);
		});
		
		assertEquals("이미 가입된 회원입니다.",e.getMessage()); //e.getMessage()는 Throwable e의 에러 문구.
	}
	
}
