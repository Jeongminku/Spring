package com.example.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.example.entity.Member;
import com.example.entity.emf.UniqueEntityManagerFactory;

public class MemberService {
	
	public void save(Member member) {
		//엔티티 매니저 팩토리: 애플리케이션 당 하나만 공유 (만들어짐)
		EntityManagerFactory emf = UniqueEntityManagerFactory.emf;
		//엔티티 매니저: 엔티티 매니저 팩토리를 생성합니다.
		EntityManager em = emf.createEntityManager();
		//트랜잭션(쪼갤 수 없는 업무의 단위)을 생성합니다.
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin(); //트랜잭션을 시작하면서 동시에 커넥션을 획득합니다.
			em.persist(member); //영속: 엔티티 매니저를 통해서 엔티티를 영속성 컨텍스트에 저장합니다. (Insert하는 기능) , 실제 반영은 커밋할때 입니다.
			tx.commit(); //실제 DB에 저장이 반영되는 커밋 입니다.
			//위의 과정에서 에러 발생시 Exception으로 보내서 에러를 출력합니다. + 롤백시킵니다.
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback(); //커밋 직전으로 롤백시킵니다.
		} finally {
			em.close(); //*준영속 입니다. 영속성 컨텍스트에서 관리하던 영속상태의 엔티티를 영속성 컨텍스트가 관리하지 않게됩니다.
			//em.detach(member);
			//em.clear();
			
//			em.remove(member);
		}
		
	}
}
