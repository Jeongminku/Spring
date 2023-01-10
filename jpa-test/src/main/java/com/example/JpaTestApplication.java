package com.example;

import java.util.Date;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.constant.MemberClass;
import com.example.entity.Member;
import com.example.entity.emf.UniqueEntityManagerFactory;
import com.example.service.MemberService;

@SpringBootApplication
public class JpaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaTestApplication.class, args);
		
		//엔티티 매니저 팩토리를 생성합니다 ("자유롭게 줄수 있는 영역") : 여기서는 hello라는 이름으로 생성함.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		//애플리케이션에서 유일한(하나의) EntityManagerFactory(엔티티 매니저 팩토리)로 설정합니다.
		UniqueEntityManagerFactory.emf = emf;
		
		//데이터를 DB에 Insert 시켜보겠습니다.
		//1. 비영속 상태의 Member 객체를 생성합니다. (영속성 콘텍스트에 들어가지 않은 멤버객체)
		Member member1 = new Member(); //비영속(new) : new 키워드를 통해 생성된 상태로 영속성 컨텍스트와 관련이 없는 상태.
		member1.setName("Lamb"); //임의의 이름.
		member1.setMemberclass(MemberClass.VIP); //열거형 클래스 가져옴. MemberClass.VIP = VIP 가 저장된다.
		member1.setDate(new Date()); //java.util.Date 임포트
		
		Member member2 = new Member();
		member2.setName("minku"); //임의의 이름.
		member2.setMemberclass(MemberClass.ADMIN); //열거형 클래스(enum) 가져옴
		member2.setDate(new Date());
		
		MemberService ms = new MemberService();
		ms.save(member1);
		ms.save(member2);
		
		//엔티티 매니저 팩토리를 종료합니다.
		UniqueEntityManagerFactory.emf.close();
 	}

}
