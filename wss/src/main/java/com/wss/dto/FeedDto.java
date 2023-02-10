package com.wss.dto;

import java.time.LocalDateTime;

import com.wss.entity.Broad;
import com.wss.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedDto {

	public FeedDto(Member member, Broad broad){
        this.broadId = broad.getId();
        this.nickname = member.getNickname();
        this.imgUrl = member.getImgUrl();
    }
	
	
	private Long id;
	private LocalDateTime feedTime;
	private String feedCon;
	
	//Broad 객체안에 있음.
	private Long broadId;
	
	//Member 객체안에 있음.
	private String nickname;
	private String imgUrl;
		
	
}
