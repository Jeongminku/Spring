package com.wss.dto;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;
import com.wss.entity.Broad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BroadFormDto {
	
	private Long id;
	
	//방송소개(스트리머 only)
	private String broadInfo;
	
	//방송 카테고리(스트리머 only)
	private String category;

	
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public Broad createBroad() {
		return modelMapper.map(this, Broad.class);
	}
}
