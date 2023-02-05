package com.wss.dto;

import org.modelmapper.ModelMapper;

import com.wss.entity.BroadcastImg;

import lombok.*;

@Getter
@Setter
public class BroadcastImgDto {

	private Long Id;
	
	private String streamUrl;	//트위치 주소

	private String streamOriImg; //오리지널 파일명
	
	private String streamImg;   //이미지
	
	private String streamImgUrl; //이미지 주소

	private String streamInfo;
	
	private String streamName;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static BroadcastImgDto of(BroadcastImg broadcast) {
		return modelMapper.map(broadcast, BroadcastImgDto.class);
	}
}
