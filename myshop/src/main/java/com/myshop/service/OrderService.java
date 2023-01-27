package com.myshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.myshop.dto.OrderDto;
import com.myshop.dto.OrderHistDto;
import com.myshop.dto.OrderItemDto;
import com.myshop.entity.Item;
import com.myshop.entity.ItemImg;
import com.myshop.entity.Member;
import com.myshop.entity.Order;
import com.myshop.entity.OrderItem;
import com.myshop.repository.ItemImgRepository;
import com.myshop.repository.ItemRepository;
import com.myshop.repository.MemberRepository;
import com.myshop.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	private final ItemRepository itemRepository;
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemImgRepository itemImgRepository;
	
	public Long order(OrderDto orderDto, String email) {
		Item item = itemRepository.findById(orderDto.getItemId()) //아이템을 먼저 찾아옵니다.
								.orElseThrow(EntityNotFoundException::new);
		
		Member member = memberRepository.findByEmail(email); //주문한 member를 찾아옵니다.
		
		List<OrderItem> orderItemList = new ArrayList<>();
		OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
		
		orderItemList.add(orderItem);
		
		Order order = Order.createOrder(member, orderItemList); //createOrder 는 Order객체를 생성해줍니다.
		orderRepository.save(order);
		
		return order.getId();
	}				
	
	@Transactional(readOnly = true)
	public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
		//주문 목록
		List<Order> orders = orderRepository.findOrders(email, pageable);

		//총 주문 목록 개수
		Long totalCount = orderRepository.countOrder(email);
		
		List<OrderHistDto> orderHistDtos = new ArrayList<>();
		
		for (Order order : orders) {
			OrderHistDto orderHistDto = new OrderHistDto(order);
			List<OrderItem> orderItems = order.getOrderItems();
			
			//상품의 대표 이미지를 가져오는 과정
			for (OrderItem orderItem : orderItems) {
				ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
				OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
				orderHistDto.addOrderItemDto(orderItemDto);
			}
			
			orderHistDtos.add(orderHistDto);
		}
		
		return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
	}
	
	
	//현재 로그인한 사용자와 주문데이터를 생성한 사용자가 같은지 한번 더 검증. 하는 메소드 입니다.
	@Transactional(readOnly = true)
	public boolean validateOrder(Long orderId, String email) {
		Member curMember = memberRepository.findByEmail(email); //로그인 한 것으로 사용자를 찾기 
		Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
		
		Member savedMember = order.getMember(); //주문한 사용자를 찾기
		
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) { //StringUtils는 thymeleaf걸로 임포트.
			return false;
		};
		
		return true;
	}
	
	//주문 취소 메소드 입니다.
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
									 .orElseThrow(EntityNotFoundException::new);
		order.cancelOrder();
	}
	
	//주문 삭제 메소드입니다. (order테이블에 있는것을 삭제하는것. order엔티티만 삭제하면 관련되있는거 다 삭제됌.)
	public void deleteOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
									.orElseThrow(EntityNotFoundException::new);
		orderRepository.delete(order);
	}
	
	
	
}
