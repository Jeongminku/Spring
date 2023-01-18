package com.myshop.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.constant.ItemSellStatus;
import com.myshop.repository.ItemRepository;
import com.myshop.repository.MemberRepository;
import com.myshop.repository.OrderItemRepository;
import com.myshop.repository.OrderRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderTest {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@PersistenceContext //영속성 컨텍스트를 사용하기 위해 선언
	EntityManager em; // 엔티티 매니저
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	public Item createItemTest() {
			Item item = new Item();		//엔티티 Item 임포트하기.
			item.setItemNm("테스트 상품");
			item.setPrice(10000);
			item.setItemDetail("테스트 상품 상세 설명");
			item.setItemSellStatus(ItemSellStatus.SELL); //열거형은 열거형에서 가져올것.
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now()); //현재날짜와 시간을 저장하는법 : LocalDateTime.Now()
			item.setUpdateTime(LocalDateTime.now());

			return item; 
	}
	

	@Test
	@DisplayName("영속성 전이 테스트입니다")
	public void cascadeTest() {
		Order order = new Order();
		
		for(int i=0; i<3; i++) {
			Item item = this.createItemTest(); //물건을 3개를 생성합니다.
			itemRepository.save(item); //만들어진 물건을 itemRepository에 save합니다.
			
			OrderItem orderItem = new OrderItem(); //OrderItem 객체를 하나 만들어둡니다.
			orderItem.setItem(item);
			orderItem.setCount(10);
			orderItem.setOrderPrice(1000);
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		
		orderRepository.saveAndFlush(order); // 영속성컨텍스트에 order엔티티를 save로 저장하면서 동시에 DB에 반영하기위해 flush 해줍니다.
		em.clear(); //영속성 컨텍스트를 초기화합니다 (비워줍니다)
		
		Order saveOrder = orderRepository.findById(order.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		assertEquals(3, saveOrder.getOrderItems().size());
	}
	
	//order엔티티를 삭제했을때 orderitem 엔티티도 삭제되는지 확인해보는 test
	public Order createOrder() {
		Order order = new Order();
		
		for(int i=0; i<3; i++) {
			Item item = this.createItemTest(); //물건을 3개를 생성합니다.
			itemRepository.save(item); 
			
			OrderItem orderItem = new OrderItem(); 
			orderItem.setItem(item);
			orderItem.setCount(10);
			orderItem.setOrderPrice(1000);
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		
		Member member = new Member();
		memberRepository.save(member);
		
		order.setMember(member); //order엔티티에 member필드에 member를 넣어주기
		orderRepository.save(order);
		
		return order;
	}
	
	@Test
	@DisplayName("고아객체 제거 테스트입니다.")
	public void orphanRemovalTest() {
		Order order = this.createOrder();
		order.getOrderItems().remove(0); //주문 엔티티에서 주문상품 엔티티를 삭제 했을때 orderItem엔티티가 삭제가 됩니다.
		em.flush();
	}
	
	@Test
	@DisplayName("지연 로딩 테스트입니다. fetch = FetchType.LAZY")
	public void lazyLoadingTest() {
		Order order = this.createOrder();
		Long orderItemId = order.getOrderItems().get(0).getId(); //order_item 테이블의 id를 구합니다.
		
		em.flush();
		em.clear();
		
		OrderItem orderItem = orderItemRepository.findById(orderItemId)
				.orElseThrow(EntityNotFoundException::new);
	}
	
}

