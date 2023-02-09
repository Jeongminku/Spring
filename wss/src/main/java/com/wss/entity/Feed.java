package com.wss.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Feed")
@Getter
@Setter
@ToString
public class Feed {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="feed_id")
	private Long id;

	
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
	
	
	private String nickname;
	
	
	private LocalDateTime feedDate;
	
	
	@Column(name="member_img")
	private String imgUrl;
}
