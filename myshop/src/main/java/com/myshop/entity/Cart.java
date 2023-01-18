package com.myshop.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 				//이것을 Entity클래스로 사용할 것이라고 어노테이션합니다.
@Table(name="cart") 	//테이블명을 설정합니다.
@Getter 				//Getter,Setter,ToString 롬복을 사용합니다.
@Setter
@ToString
public class Cart {

	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
