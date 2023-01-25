package com.myshop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.myshop.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 				//이것을 Entity클래스로 사용할 것이라고 어노테이션합니다.
@Table(name="orders") 	//테이블명을 설정합니다.
@Getter 				//Getter,Setter,ToString 롬복을 사용합니다.
@Setter
@ToString
public class Order {
	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;	
	
	private LocalDateTime orderDate; //주문일
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; //주문상태
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) //OrderItem에 있는 order에 의해 관리가 됩니다.
	private List<OrderItem> orderItems = new ArrayList<>();
	
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem); //양방향 참조 관계라면 add를 해준다음에
		orderItem.setOrder(this); //양방향 참조 관계라면 orderItem객체에도 order객체를 세팅을 해줘야한대요(?)
		//★선생님코멘트 :  양방향 참조관계일때는 orderItem객체에도 order객체를 세팅하여야합니다.
		
	}
	//order객체를 생성해주는 createOrder 입니다.
	public static Order createOrder(Member member, List<OrderItem> orderItemList) {
		Order order = new Order();
		order.setMember(member); //멤버테이블과 관계가 있기때문에 멤버테이블도 넣어주어야. (@JoinColumn)
		
		for(OrderItem orderItem : orderItemList) { //orderItemList에는 주문한 리스트가 들어가있을것.
			order.addOrderItem(orderItem);
		}
		
		order.setOrderStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}
	
	//총 주문금액
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
	
 }
