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
 }
