package com.wss.dto;

import java.time.LocalDateTime;

import com.wss.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedDto {

	public FeedDto(Member member){
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.imgUrl = member.getImgUrl();
    }
	
	
	private Long id;

	private Long memberId;
	
	private String nickname;
	
	private LocalDateTime feedDate;
	
	private String imgUrl;
}
