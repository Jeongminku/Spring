package com.myshop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	//Query(안에는 select 쿼리문 작성   Order = 오더엔티티 뒤에는 약칭 o)
	@Query("select o from Order o where o.member.email = :email order by o.orderDate desc")
	List<Order> findOrders(@Param("email") String email, Pageable pageable); //현재 로그인 한 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회합니다.
	//@Param 어노테이션을 붙여서 "email"이라는 이름으로 Param을 쓰겠다고 표현하기.
	
	@Query("select count(o) from Order o where o.member.email = :email")
	Long countOrder(@Param("email") String email); //현재 로그인 한 회원의 주문 개수가 몇개인지 조회합니다.
}
