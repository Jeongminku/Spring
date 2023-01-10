package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
public class Item {
	
	@Id
	@Column (name="item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (name="name", nullable = false)
	private String name;
	
	@Column (name="price", nullable = false)
	private int price;
	
	@Column (name="stockquantity", nullable = false)
	private int stockQuantity;
	
}
