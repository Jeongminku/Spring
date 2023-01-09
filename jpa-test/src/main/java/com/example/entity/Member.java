package com.example.entity;

import java.util.Date;

import javax.persistence.*;

import com.example.constant.MemberClass;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
	
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column
	private MemberClass memberclass;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
}
