package com.myshop.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.repository.MemberRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class MemberTest {

	@Autowired
	MemberRepository memberRepository;
	
	@PersistenceContext
	EntityManager em;
	
	@Test
	@DisplayName("auditing 테스트")
	@WithMockUser(username = "Gara", roles = "USER") //스프링시큐리티에서 지원하는 어노테이션으로 임의의 회원을 역할을 부여하여 로그인 한 것처럼 보이게 함.
	public void auditingTest() {
		Member newMember = new Member();
		memberRepository.save(newMember);
		
		em.flush();
		em.clear();
		
		Member member = memberRepository.findById(newMember.getId())
				.orElseThrow(EntityNotFoundException::new);
		System.out.println("등록시간: " + member.getRegTime());
		System.out.println("수정시간: " + member.getUpDateTime());
		System.out.println("등록한 사람: " + member.getCreatedBy());
		System.out.println("수정한 사람: " + member.getModifiedBy());
	}
}
