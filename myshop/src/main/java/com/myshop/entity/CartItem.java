package com.myshop.entity;

import javax.persistence.*;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cart_item")
@Getter
@Setter
@ToString
public class CartItem {
	
	@Id
	@Column(name="cart_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO) //생성전략 AUTO
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cart_id")
	private Cart cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	private int count; 
}
