package com.myshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myshop.dto.OrderDto;
import com.myshop.entity.Item;
import com.myshop.entity.Member;
import com.myshop.entity.Order;
import com.myshop.entity.OrderItem;
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
}
