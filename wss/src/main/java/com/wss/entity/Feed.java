package com.wss.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import com.wss.dto.FeedDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Feed")
@Getter
@Setter
@ToString
public class Feed extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="feed_id")
	private Long id;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime feedTime;
	
	private String feedcon;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member; //멤버객체안에 이것저것잇음.	
	
	@ManyToOne
	@JoinColumn(name="broad_id")
	private Broad broad;
	
//	public static Feed createFeed(FeedDto feedDto, Member member) {
//		Feed feed = new Feed();
//		feed.setId(feedDto.getId());
//		feed.setFeedTime(feedDto.getFeedTime());
//		feed.setFeedcon(feedDto.getFeedCon());
//		
//		feed.setMember(member);
//		
//		return feed;
//	}
	
	public static Feed createFeed(String feedcon, Member member, Broad broad) {
		Feed feed = new Feed();
		feed.setFeedcon(feedcon);
		//feed.setFeedTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")).withNano(0));
		feed.setMember(member);
		feed.setBroad(broad);
		
		return feed;
	}
}
