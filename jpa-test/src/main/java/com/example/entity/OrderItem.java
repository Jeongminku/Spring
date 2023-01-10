package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name="OrderItem")
@Setter
@Getter
@ToString
public class OrderItem {
	@Id
	@Column(name = "order_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "order_id", nullable = false)
	private Long orderId;
	
	@Column(name = "item_id", nullable = false)
	private Long itemId;
	
	@Column(name = "orderprice", nullable = false)
	private int orderPrice;
	
	@Column(name = "count", nullable = false)
	private int count;
}
