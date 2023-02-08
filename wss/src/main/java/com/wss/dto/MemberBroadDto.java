package com.wss.dto;

import com.wss.entity.Broad;
import com.wss.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberBroadDto {

	Member member;
	
	Broad Broad;
}
