package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Orders")
@Getter
@Setter
@ToString
public class Order {

	@Id 											//PK줌.
	@Column(name="order_id", nullable = false) 						//컬럼명 설정
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="member_id", nullable = false)
	private Long memberId;
	
	@Column(name="orderdate", nullable = false)
	private Date orderDate;
	
	@Column(name="status", nullable = false)
	private OrderStatus status;
}
