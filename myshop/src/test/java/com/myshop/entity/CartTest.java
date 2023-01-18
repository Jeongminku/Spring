package com.myshop.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.dto.MemberFormDto;
import com.myshop.repository.CartRepository;
import com.myshop.repository.MemberRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class CartTest {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PersistenceContext //영속성 컨텍스트를 사용하기 위해 선언
	EntityManager em; // 엔티티 매니저
	
	public Member createMember() {
		MemberFormDto member = new MemberFormDto();
		member.setName("홍길동");
		member.setEmail("gara@email.com");
		member.setAddress("서울시 가라구 구라동");
		member.setPassword("1234");
		
		return Member.createMember(member, passwordEncoder);
	}
	
	@Test
	@DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
	public void findCartAndMemberTest() {
		Member member = createMember();  //멤버를 하나 생성하여 만듭니다.
		memberRepository.save(member);	 //위의 멤버 객체를 save(insert)합니다.
  	    //save: 데이터가 있는데 바뀐게 있으면 그 부분을 바꿔주고, 데이터가 없다면 새로 생성해줍니다.
		
		Cart cart = new Cart();  //카트 객체를 생성함
		cart.setMember(member);	 //카트가 멤버필드를 갖고있었으므로 카트안에 멤버를 넣어줌
		cartRepository.save(cart); //다시 카트를 save 함.
		
		em.flush(); //트랜잭션이 끝날 때 데이터베이스에 반영합니다.
		em.clear(); //영속성 컨텍스트를 비워줍니다. -> 실제 데이터베이스에서 장바구니 entity를 가지고 올 때 회원 entity도 같이 가지고 오는지 보기 위해서.
		
		Cart savedCart = cartRepository.findById(cart.getId())
				.orElseThrow(EntityNotFoundException::new); //옵셔널 기능(?)
		
		assertEquals(savedCart.getMember().getId(), member.getId()); //assertEquals를 통해서 비교합니다.
		
	}
}
