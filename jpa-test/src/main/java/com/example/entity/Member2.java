package com.example.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name="Memeber2")
@Getter
@Setter
@ToString

public class Member2 {
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="name", nullable = false)
	private String name;
	
	
	@Column(name="city", nullable = false)
	private String city;
	
	
	@Column(name="street", nullable = false)
	private String street;
	
	
	@Column(name="zipcode", nullable = false)
	private String zipcode;
	
}
